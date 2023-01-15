package kr.disdong.knitting.auth.module.auth.v1.kakao.service

import kr.disdong.knitting.auth.kakao.KakaoService
import kr.disdong.knitting.auth.kakao.dto.OAuthCallbackResponse
import kr.disdong.knitting.auth.module.auth.v1.kakao.dto.LoginResponse
import kr.disdong.knitting.auth.module.auth.v1.kakao.extension.toLoginResponse
import kr.disdong.knitting.common.logger.logger
import kr.disdong.knitting.domain.jpa.domain.OauthType
import kr.disdong.knitting.domain.jpa.domain.UserEntity
import kr.disdong.knitting.domain.jpa.domain.UserOauthMetadata
import kr.disdong.knitting.domain.jpa.repository.UserOauthMetadataRepository
import kr.disdong.knitting.domain.jpa.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val kakaoService: KakaoService,
    private val userRepository: UserRepository,
    private val userOauthMetadataRepository: UserOauthMetadataRepository
) {

    private val logger = logger<AuthService>()

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
            val metadata = userOauthMetadataRepository.save(
                UserOauthMetadata(
                    id = idToken.sub,
                    nickname = idToken.nickname,
                    type = OauthType.KAKAO
                )
            )

            user = userRepository.save(UserEntity(
                userOauthMetadata = metadata
            ))
        }

        logger.info("user: $user")

        return user!!.toLoginResponse()
    }
}
