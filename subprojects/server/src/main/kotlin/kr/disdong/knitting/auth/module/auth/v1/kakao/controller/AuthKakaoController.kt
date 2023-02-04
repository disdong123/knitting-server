package kr.disdong.knitting.auth.module.auth.v1.kakao.controller

import jakarta.servlet.http.HttpServletResponse
import kr.disdong.knitting.auth.kakao.v1.KakaoService
import kr.disdong.knitting.auth.kakao.v1.dto.OAuthCallbackResponse
import kr.disdong.knitting.auth.module.auth.v1.kakao.dto.LoginResponse
import kr.disdong.knitting.auth.module.auth.v1.kakao.service.AuthKakaoService
import kr.disdong.knitting.common.dto.KnittingResponse
import kr.disdong.knitting.common.logger.logger
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/auth/kakao")
class AuthKakaoController(
    private val authKakaoService: AuthKakaoService,
    private val kakaoService: KakaoService,
) {

    private val logger = logger<AuthKakaoController>()

    /**
     * kakao 로그인 페이지로 리다이렉트합니다.
     * @param httpServletResponse
     */
    @GetMapping("/login")
    fun login(httpServletResponse: HttpServletResponse) {
        logger.info("login()")

        kakaoService.login(httpServletResponse)
    }

    /**
     * 유저가 카카오 로그인 완료 시, 리다이렉트되는 api 입니다.
     * @param response
     */
    @GetMapping("/callback")
    fun callback(response: OAuthCallbackResponse): KnittingResponse<LoginResponse> {
        logger.info("callback(response: $response)")

        return KnittingResponse.of(authKakaoService.login(response))
    }

    /**
     * TODO header 로 변경해야합니다.
     *
     * @param token
     * @return
     */
    @GetMapping("/logout")
    // fun logout(@RequestParam token: String): ResponseEntity<LogoutResponse> {
    //     return kakaoService.logout(token)
    // }
    fun logout(@RequestHeader("Authentication") token: String) {
        logger.info("token: $token")
    }
}
