package kr.disdong.knitting.auth.kakao.dto

/**
 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code-response
 * @property code
 * @property state
 * @property error
 * @property error_description
 */
data class OAuthCallbackResponse(
    val code: String?,
    val state: String?,
    val error: String?,
    val error_description: String?,
    val redirectUri: String = "http://127.0.0.1:8080/api/v1/auth/kakao/callback",
)
