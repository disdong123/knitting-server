package kr.disdong.knitting.auth.kakao

import jakarta.servlet.http.HttpServletResponse
import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.auth.kakao.dto.LoginResponse
import kr.disdong.knitting.auth.kakao.dto.OAuthCallbackResponse
import kr.disdong.knitting.auth.kakao.spec.KakaoSpec
import kr.disdong.knitting.common.annotation.AuthGuard
import kr.disdong.knitting.common.annotation.CurrentUserClaims
import kr.disdong.knitting.common.dto.KnittingResponse
import kr.disdong.knitting.common.logger.logger
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/auth/kakao")
class KakaoController(
    private val kakaoService: KakaoService,
) : KakaoSpec {

    private val logger = logger<KakaoController>()

    /**
     * kakao 로그인 페이지로 리다이렉트합니다.
     * 서버 테스트용으로, 클라이언트에서 할 수도 있습니다.
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
        return KnittingResponse.of(kakaoService.callback(response))
    }

    /**
     * 브라우저와 kakao 와의 세션을 완전히 끊을 수도 있습니다.
     * 서버 테스트용으로, 클라이언트에서 할 수도 있습니다.
     * @param httpServletResponse
     */
    @GetMapping("/logout")
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
        kakaoService.logout(state)
        return KnittingResponse.of()
    }
}
