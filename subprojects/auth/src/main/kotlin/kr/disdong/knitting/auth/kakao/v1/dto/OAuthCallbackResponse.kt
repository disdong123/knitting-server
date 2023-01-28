package kr.disdong.knitting.auth.kakao.v1.dto

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
)
