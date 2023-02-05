package kr.disdong.knitting.server.module.auth.kakao.service

import kr.disdong.knitting.auth.kakao.KakaoService
import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.auth.kakao.dto.OAuthCallbackResponse
import kr.disdong.knitting.server.module.auth.kakao.dto.LoginResponse
import kr.disdong.knitting.server.module.auth.kakao.extension.toLoginResponse
import kr.disdong.knitting.common.logger.logger
import kr.disdong.knitting.common.time.Millis
import kr.disdong.knitting.common.token.TokenManager
import kr.disdong.knitting.domain.jpa.domain.OauthType
import kr.disdong.knitting.domain.jpa.domain.UserEntity
import kr.disdong.knitting.domain.jpa.domain.UserOauthMetadataEntity
import kr.disdong.knitting.domain.jpa.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthKakaoService(
    private val kakaoService: KakaoService,
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager<AccessTokenClaims>,
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
        val refreshToken = tokenManager.create("user", AccessTokenClaims(user.id!!), Millis.TWO_WEEKS)

        logger.info("token: ${accessToken.value} ${refreshToken.value}")

        // TODO 토큰 생성후, 발급해줘야한다.
        user.userOauthMetadata.refreshToken = refreshToken
        user.userOauthMetadata.accessToken = accessToken

        return user.toLoginResponse(accessToken)
    }
}
