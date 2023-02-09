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

        /**
         * TODO.
         *  1. 최초 로그인한 회원이면 일단 데이터 저장하고 반환해준다.
         *      토큰은 다음 요청에서 닉네임을 발게 되면 그때 완전한 회원가입을 했다고 생각하고 토큰을 준다.
         */
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

        val accessToken = tokenManager.create("user", AccessTokenClaims(user!!.id!!), Millis.HOUR)
        val refreshToken = Token(response.refreshToken)

        logger.info("token: ${accessToken.value} ${refreshToken.value}")

        user.userOauthMetadata.accessToken = accessToken
        user.userOauthMetadata.refreshToken = refreshToken

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
}
