package kr.disdong.knitting.auth.module.auth.v1.kakao.controller

import jakarta.servlet.http.HttpServletResponse
import kr.disdong.knitting.auth.kakao.KakaoService
import kr.disdong.knitting.auth.kakao.dto.LogoutResponse
import kr.disdong.knitting.auth.kakao.dto.OAuthCallbackResponse
import kr.disdong.knitting.auth.module.auth.v1.kakao.dto.LoginResponse
import kr.disdong.knitting.auth.module.auth.v1.kakao.service.AuthService
import kr.disdong.knitting.common.logger.logger
import kr.disdong.knitting.domain.jpa.domain.OauthType
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/auth/kakao")
class AuthController(
    private val authService: AuthService,
    private val kakaoService: KakaoService,
) {

    private val logger = logger<AuthController>()

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
    fun callback(response: OAuthCallbackResponse): LoginResponse {
        logger.info("callback(response: $response)")

        return authService.login(response)
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
