package kr.disdong.knitting.server.module.auth.kakao.service

import com.fasterxml.jackson.core.type.TypeReference
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import kr.disdong.knitting.auth.common.exception.InvalidAccessTokenException
import kr.disdong.knitting.auth.kakao.KakaoService
import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.auth.kakao.dto.OAuthCallbackResponse
import kr.disdong.knitting.common.logger.logger
import kr.disdong.knitting.common.time.Millis
import kr.disdong.knitting.common.token.Token
import kr.disdong.knitting.common.token.TokenManager
import kr.disdong.knitting.domain.jpa.domain.OauthType
import kr.disdong.knitting.domain.jpa.domain.UserEntity
import kr.disdong.knitting.domain.jpa.domain.UserOauthMetadataEntity
import kr.disdong.knitting.domain.jpa.repository.UserRepository
import kr.disdong.knitting.server.module.auth.kakao.dto.LoginResponse
import kr.disdong.knitting.server.module.auth.kakao.exception.UserNotFoundException
import kr.disdong.knitting.server.module.auth.kakao.extension.toLoginResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthKakaoService(
    private val kakaoService: KakaoService,
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager,
) {

    private val logger = logger<AuthKakaoService>()

    /**
     *
     * @param oAuthCallbackResponse
     */
    @Transactional
    fun login(oAuthCallbackResponse: OAuthCallbackResponse): LoginResponse {
        val response = kakaoService.getToken(oAuthCallbackResponse)

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

                val response = kakaoService.refreshAccessToken(user.userOauthMetadata.refreshToken!!)

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
}
