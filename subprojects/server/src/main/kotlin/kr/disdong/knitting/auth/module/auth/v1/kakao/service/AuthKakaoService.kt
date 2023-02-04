package kr.disdong.knitting.auth.module.auth.v1.kakao.service

import kr.disdong.knitting.auth.kakao.v1.KakaoService
import kr.disdong.knitting.auth.kakao.v1.dto.OAuthCallbackResponse
import kr.disdong.knitting.auth.module.auth.v1.kakao.dto.LoginResponse
import kr.disdong.knitting.auth.module.auth.v1.kakao.extension.toLoginResponse
import kr.disdong.knitting.common.logger.logger
import kr.disdong.knitting.domain.jpa.domain.OauthType
import kr.disdong.knitting.domain.jpa.domain.UserEntity
import kr.disdong.knitting.domain.jpa.domain.UserOauthMetadataEntity
import kr.disdong.knitting.domain.jpa.repository.UserOauthMetadataRepository
import kr.disdong.knitting.domain.jpa.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthKakaoService(
    private val kakaoService: KakaoService,
    private val userRepository: UserRepository,
    private val userOauthMetadataRepository: UserOauthMetadataRepository
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

        println(user)

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

        return user!!.toLoginResponse()
    }
}
