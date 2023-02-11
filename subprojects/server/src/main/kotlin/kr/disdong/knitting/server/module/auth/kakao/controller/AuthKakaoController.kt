package kr.disdong.knitting.server.module.auth.kakao.controller

import jakarta.servlet.http.HttpServletResponse
import kr.disdong.knitting.auth.kakao.KakaoService
import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.auth.kakao.dto.OAuthCallbackResponse
import kr.disdong.knitting.common.dto.KnittingResponse
import kr.disdong.knitting.common.logger.logger
import kr.disdong.knitting.server.common.annotation.AuthGuard
import kr.disdong.knitting.server.common.annotation.CurrentUserClaims
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
    fun login(httpServletResponse: HttpServletResponse): KnittingResponse<Unit> {
        logger.info("login()")
        kakaoService.login(httpServletResponse)
        return KnittingResponse.of()
    }

    /**
     * 유저가 카카오 로그인 완료 시, 리다이렉트되는 api 입니다.
     * @param response
     */
    @GetMapping("/callback")
    override fun callback(response: OAuthCallbackResponse): KnittingResponse<LoginResponse> {
        logger.info("login(response: $response) ${response.code}")
        return KnittingResponse.of(authKakaoService.login(response))
    }

    /**
     * 브라우저와 kakao 와의 세션을 완전히 끊을 수도 있습니다.
     * TODO 카카오로 요청보낼때 유저의 어떠한 값도 보내지 않는데, 다른 유저는 로그아웃 안되는지 확인 필요.
     * @param httpServletResponse
     */
    @GetMapping("/logout-with-kakao")
    @AuthGuard
    fun logoutWithKakao(
        httpServletResponse: HttpServletResponse,
        @CurrentUserClaims claims: AccessTokenClaims,
    ): KnittingResponse<Unit> {
        logger.info("logout(claims: $claims)")
        kakaoService.logoutWithKakao(httpServletResponse, claims)
        return KnittingResponse.of()
    }

    /**
     *
     * @param state user id 값입니다. logout-with-kakao 에서 넘겨준 state 를 받습니다.
     * @return
     */
    @GetMapping("/logout/callback")
    fun logoutCallback(state: Long): KnittingResponse<Unit> {
        logger.info("logoutCallback(state: $state}")
        authKakaoService.logout(state)
        return KnittingResponse.of()
    }
}
