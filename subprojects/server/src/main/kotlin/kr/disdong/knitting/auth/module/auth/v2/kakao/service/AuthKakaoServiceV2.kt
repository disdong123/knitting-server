package kr.disdong.knitting.auth.module.auth.v2.kakao.service

import kr.disdong.knitting.auth.kakao.v2.KakaoServiceV2
import kr.disdong.knitting.auth.kakao.v2.dto.OAuthCallbackResponseV2
import kr.disdong.knitting.auth.module.auth.v2.kakao.dto.LoginResponseV2
import kr.disdong.knitting.auth.module.auth.v2.kakao.extension.toLoginResponseV2
import kr.disdong.knitting.common.logger.logger
import kr.disdong.knitting.domain.jpa.domain.OauthType
import kr.disdong.knitting.domain.jpa.domain.UserEntity
import kr.disdong.knitting.domain.jpa.domain.UserOauthMetadata
import kr.disdong.knitting.domain.jpa.repository.UserOauthMetadataRepository
import kr.disdong.knitting.domain.jpa.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthKakaoServiceV2(
    private val kakaoServiceV2: KakaoServiceV2,
    private val userRepository: UserRepository,
    private val userOauthMetadataRepository: UserOauthMetadataRepository
) {

    private val logger = logger<AuthKakaoServiceV2>()

    /**
     *
     * @param oAuthCallbackResponse
     */
    @Transactional
    fun login(oAuthCallbackResponse: OAuthCallbackResponseV2): LoginResponseV2 {
        val response = kakaoServiceV2.getToken(oAuthCallbackResponse)

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

            user = userRepository.save(
                UserEntity(
                    userOauthMetadata = metadata
                )
            )
        }

        logger.info("user: $user")

        return user!!.toLoginResponseV2()
    }
}
