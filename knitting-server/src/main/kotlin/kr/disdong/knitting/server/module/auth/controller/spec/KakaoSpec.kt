package kr.disdong.knitting.server.module.auth.controller.spec

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.auth.kakao.dto.LoginResponse
import kr.disdong.knitting.auth.kakao.dto.OAuthCallbackResponse
import kr.disdong.knitting.common.annotation.CurrentUserClaims
import kr.disdong.knitting.common.dto.KnittingResponse

@Tag(name = "인증", description = "인증용 api 입니다.")
interface KakaoSpec {

    @Operation(
        hidden = true
    )
    fun login(httpServletResponse: HttpServletResponse): KnittingResponse<Unit>

    @Operation(
        summary = "유저가 카카오 로그인 했을 때 redirect 되는 api",
        parameters = [
            Parameter(
                name = "code",
                description = "인가코드 (kakao)",
            ),
            Parameter(
                name = "state",
                description = "상태 (kakao)",
            ),
            Parameter(
                name = "error",
                description = "에러 (kakao)",
            ),
            Parameter(
                name = "error_description",
                description = "에러 설명 (kakao)",
            ),
            Parameter(
                name = "redirectUri",
                description = "리다이렉트 uri",
                example = "http://localhost:8080",
            )
        ],
        responses = []
    )
    // @ApiResponses(
    //     value = [
    //         ApiResponse(
    //             responseCode = "200",
    //             description = "카카오 로그인 후, redirect 되는 api 입니다.",
    //             content = [
    //                 Content(schema = Schema(implementation = LoginResponse::class))
    //             ]
    //         )
    //     ]
    // )
    fun callback(response: OAuthCallbackResponse): KnittingResponse<LoginResponse>

    @Operation(hidden = true)
    fun logoutWithKakao(
        httpServletResponse: HttpServletResponse,
        @CurrentUserClaims claims: AccessTokenClaims,
    ): KnittingResponse<Unit>

    @Operation(
        summary = "유저가 카카오 로그아웃 했을 때 redirect 되는 api",
    )
    fun logoutCallback(state: Long): KnittingResponse<Unit>
}
