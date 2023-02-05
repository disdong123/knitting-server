package kr.disdong.knitting.server.module.auth.kakao.controller.spec

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.disdong.knitting.auth.kakao.dto.OAuthCallbackResponse
import kr.disdong.knitting.common.dto.KnittingResponse
import kr.disdong.knitting.server.module.auth.kakao.dto.LoginResponse
import org.springdoc.core.annotations.ParameterObject

@Tag(name = "인증", description = "인증용 api 입니다.")
interface AuthKakaoControllerSpec {

    @Operation(
        summary = "카카오 로그인 후, redirect 되는 api",
        parameters = [
            Parameter(
                name = "code",
                description = "인가코드 (kakao)",
                `in` = ParameterIn.QUERY,
            ),
            Parameter(
                name = "state",
                description = "상태 (kakao)",
                `in` = ParameterIn.QUERY,
            ),
            Parameter(
                name = "error",
                description = "에러 (kakao)",
                `in` = ParameterIn.QUERY,
            ),
            Parameter(
                name = "error_description",
                description = "에러 설명 (kakao)",
                `in` = ParameterIn.QUERY,
            ),
            Parameter(
                name = "redirectUri",
                description = "리다이렉트 uri",
                `in` = ParameterIn.QUERY,
                example = "http://localhost:8080"
            )
        ]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "카카오 로그인 후, redirect 되는 api 입니다.",
                content = [
                    Content(schema = Schema(implementation = LoginResponse::class))
                ]
            )
        ]
    )
    fun callback(@ParameterObject response: OAuthCallbackResponse): KnittingResponse<LoginResponse>
}
