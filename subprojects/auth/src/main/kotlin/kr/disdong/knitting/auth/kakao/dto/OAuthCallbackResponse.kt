package kr.disdong.knitting.auth.kakao.dto

import io.swagger.v3.oas.annotations.media.Schema

/**
 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code-response
 * @property code
 * @property state
 * @property error
 * @property error_description
 */
@Schema(description = "login 요청값입니다.")
class OAuthCallbackResponse(
    val code: String?,
    val state: String?,
    val error: String?,
    val error_description: String?,
    val redirectUri: String = "http://127.0.0.1:8080/api/v1/auth/kakao/callback",
)
