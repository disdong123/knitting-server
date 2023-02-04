package kr.disdong.knitting.auth.module.auth.v2.kakao.service

import kr.disdong.knitting.auth.kakao.v2.KakaoServiceV2
import kr.disdong.knitting.auth.kakao.v2.dto.OAuthCallbackResponseV2
import kr.disdong.knitting.auth.module.auth.v2.kakao.dto.LoginResponseV2
import kr.disdong.knitting.auth.module.auth.v2.kakao.extension.toLoginResponseV2
import kr.disdong.knitting.common.logger.logger
import kr.disdong.knitting.common.time.Millis
import kr.disdong.knitting.common.token.TokenManager
import kr.disdong.knitting.domain.jpa.domain.OauthType
import kr.disdong.knitting.domain.jpa.repository.UserOauthMetadataRepository
import kr.disdong.knitting.domain.jpa.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthKakaoServiceV2(
    private val kakaoServiceV2: KakaoServiceV2,
    private val userRepository: UserRepository,
    private val userOauthMetadataRepository: UserOauthMetadataRepository,
    private val tokenManager: TokenManager<AccessTokenClaims>,
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

        /**
         * TODO.
         *  1. 최초 로그인한 회원이면 일단 데이터 저장하고 반환해준다.
         *      토큰은 다음 요청에서 닉네임을 발게 되면 그때 완전한 회원가입을 했다고 생각하고 토큰을 준다.
         *  2. oneTOone 에서 user_oatu_metadata 에 user_id 가 있는게 더 맞는거같긴한데,     user_oatu_metadata_id 가 계속 생김... 그리고 조인도 확인해야함.
         */
        if (user == null) {
            // TODO. 왜 같이 save 안될까??
            // val metadata = userOauthMetadataRepository.save(
            //     UserOauthMetadata(
            //         id = idToken.sub,
            //         nickname = idToken.nickname,
            //         type = OauthType.KAKAO
            //     )
            // )
            //
            // user = userRepository.save(
            //     UserEntity(
            //         userOauthMetadata = metadata
            //     )
            // )
        }

        logger.info("user: $user")

        val accessToken = tokenManager.create("user", AccessTokenClaims(user!!.id!!, user.name!!), Millis.HOUR)
        val refreshToken = tokenManager.create("user", AccessTokenClaims(user.id!!, user.name!!), Millis.TWO_WEEKS)

        logger.info("token: ${accessToken.value} ${refreshToken.value}")

        // TODO 토큰 생성후, 발급해줘야한다.
        user.userOauthMetadata.refreshToken = refreshToken

        return user.toLoginResponseV2(accessToken)
    }
}

class AccessTokenClaims(
    val id: Long,
    val nickname: String,
)