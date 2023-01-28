package kr.disdong.knitting.auth.module.auth.v2.kakao.controller

import kr.disdong.knitting.auth.kakao.v2.KakaoServiceV2
import kr.disdong.knitting.auth.kakao.v2.dto.OAuthCallbackResponseV2
import kr.disdong.knitting.auth.module.auth.v2.kakao.dto.LoginResponseV2
import kr.disdong.knitting.auth.module.auth.v2.kakao.service.AuthKakaoServiceV2
import kr.disdong.knitting.common.logger.logger
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v2/auth/kakao")
class AuthKakaoControllerV2(
    private val authKakaoServiceV2: AuthKakaoServiceV2,
    private val kakaoServiceV2: KakaoServiceV2,
) {

    private val logger = logger<AuthKakaoControllerV2>()

    /**
     * 유저가 카카오 로그인 완료 시, 리다이렉트되는 api 입니다.
     * @param response
     */
    @GetMapping("/callback")
    fun login(response: OAuthCallbackResponseV2): LoginResponseV2 {
        logger.info("login(response: $response)")

        return authKakaoServiceV2.login(response)
    }

    /**
     *
     * @param token
     * @return
     */
    @GetMapping("/logout")
    fun logout(@RequestHeader("Authentication") token: String) {
        logger.info("token: $token")
    }
}
