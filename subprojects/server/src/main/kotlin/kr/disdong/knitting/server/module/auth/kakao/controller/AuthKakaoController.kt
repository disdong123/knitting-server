package kr.disdong.knitting.server.module.auth.kakao.controller

import jakarta.servlet.http.HttpServletResponse
import kr.disdong.knitting.auth.kakao.KakaoService
import kr.disdong.knitting.auth.kakao.dto.OAuthCallbackResponse
import kr.disdong.knitting.common.dto.KnittingResponse
import kr.disdong.knitting.common.logger.logger
import kr.disdong.knitting.common.token.Token
import kr.disdong.knitting.server.module.auth.kakao.controller.spec.AuthKakaoControllerSpec
import kr.disdong.knitting.server.module.auth.kakao.dto.LoginResponse
import kr.disdong.knitting.server.module.auth.kakao.service.AuthKakaoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/auth/kakao")
class AuthKakaoController(
    private val authKakaoService: AuthKakaoService,
    private val kakaoService: KakaoService,
) : AuthKakaoControllerSpec {

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
    override fun callback(response: OAuthCallbackResponse): KnittingResponse<LoginResponse> {
        logger.info("login(response: $response)")

        return KnittingResponse.of(authKakaoService.login(response))
    }

    /**
     * TODO header 로 변경해야합니다.
     *
     * @param token
     * @return
     */
    @GetMapping("/logout")
    fun logout(@RequestParam token: Token) {
        println(token.value)
        // return kakaoService.logout(token)
    }
}
