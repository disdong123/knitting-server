package kr.disdong.knitting.auth.kakao

import com.fasterxml.jackson.core.type.TypeReference
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import jakarta.servlet.http.HttpServletResponse
import kr.disdong.knitting.auth.common.exception.AuthorizationCodeAccessDeniedException
import kr.disdong.knitting.auth.common.exception.InvalidAccessTokenException
import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.auth.kakao.dto.LoginResponse
import kr.disdong.knitting.auth.kakao.dto.OAuthCallbackResponse
import kr.disdong.knitting.auth.kakao.dto.TokenResponse
import kr.disdong.knitting.auth.kakao.exception.UserNotFoundException
import kr.disdong.knitting.auth.kakao.extension.toLoginResponse
import kr.disdong.knitting.common.logger.logger
import kr.disdong.knitting.common.time.Millis
import kr.disdong.knitting.common.token.Token
import kr.disdong.knitting.common.token.TokenManager
import kr.disdong.knitting.mysql.domain.user.model.OauthType
import kr.disdong.knitting.mysql.domain.user.model.UserEntity
import kr.disdong.knitting.mysql.domain.user.model.UserOauthMetadataEntity
import kr.disdong.knitting.mysql.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class KakaoService(
    private val kakaoClient: KakaoClient,
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager,
) {

    private val logger = logger<KakaoService>()

    /**
     *
     * @param httpServletResponse
     */
    fun login(httpServletResponse: HttpServletResponse) {
        kakaoClient.login(httpServletResponse)
    }

    /**
     *
     * @param oAuthCallbackResponse
     */
    @Transactional
    fun callback(oAuthCallbackResponse: OAuthCallbackResponse): LoginResponse {
        val response = getToken(oAuthCallbackResponse)

        val idToken = response.decodeIdToken()

        logger.info("idToken: $idToken")

        var user = userRepository.findByIdAndType(idToken.sub, OauthType.KAKAO)

        if (user == null) {
            val userOauthMetadata = UserOauthMetadataEntity(
                id = idToken.sub,
                nickname = idToken.nickname,
                type = OauthType.KAKAO,
            )

            val userEntity = UserEntity(userOauthMetadata = userOauthMetadata)

            userOauthMetadata.user = userEntity
            user = userRepository.save(userEntity)
        }

        logger.info("user: $user")

        // 카카오에서 받은 access token 은 사용하지 않습니다.
        val accessToken = tokenManager.create("user", AccessTokenClaims(user!!.id!!), Millis.HOUR)
        val refreshToken = response.refreshToken

        logger.info("token: ${accessToken.value} ${refreshToken.value}")

        user.setTokens(accessToken, refreshToken)

        return user.toLoginResponse(accessToken)
    }

    /**
     * 토큰이 유효기간이 만료되었으면 refresh token 을 이용하여 access token 을 다시 받아옵니다.
     * 서비스의 access token 과 카카오의 access token 의 유효기간을 동일하게 가져갑니다.
     * @param token
     */
    @Transactional
    fun verifyAccessToken(token: Token): AccessTokenClaims {
        try {
            return tokenManager.getCustomClaims(
                token,
                object : TypeReference<AccessTokenClaims>() {}
            )
        } catch (e: JwtException) {
            if (e is ExpiredJwtException) {
                val user = userRepository.findByAccessToken(token) ?: throw InvalidAccessTokenException(token)

                val response = kakaoClient.refreshAccessToken(user.userOauthMetadata.refreshToken!!)

                user.userOauthMetadata.accessToken = tokenManager.create("user", AccessTokenClaims(user!!.id!!), Millis.HOUR)
                if (response.refreshToken != null) {
                    user.userOauthMetadata.refreshToken = response.refreshToken
                }

                return AccessTokenClaims(user.id!!)
            }

            throw e
        }
    }

    /**
     * 카카오로 부터 리다이렉트되면 유저가 가지고 있는 토큰을 모두 삭제합니다.
     * @param id
     */
    @Transactional
    fun logout(id: Long) {
        val user = userRepository.findByUserId(id) ?: throw UserNotFoundException(id)
        user.removeTokens()
    }

    /**
     *
     * @param httpServletResponse
     * @param claims
     */
    fun logoutWithKakao(httpServletResponse: HttpServletResponse, claims: AccessTokenClaims) {
        kakaoClient.logoutWithKakao(httpServletResponse, claims)
    }

    /**
     * access token 을 가져옵니다.
     * @param response
     */
    private fun getToken(response: OAuthCallbackResponse): TokenResponse {
        logger.info("getToken: $response")

        if (response.isAccessDenied()) {
            logger.error("response.error_description: ${response.error_description}")
            throw AuthorizationCodeAccessDeniedException()
        }

        val tokenResponse = kakaoClient.getTokenWithAuthCode(response)

        logger.info("tokenResponse: $tokenResponse")

        return tokenResponse
    }
}
