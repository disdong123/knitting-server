package kr.disdong.knitting.auth.kakao.v1.dto

/**
 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token-request
 * @property client_id
 * @property redirect_uri
 * @property code
 * @property client_secret
 * @property grant_type
 */
data class AccessTokenRequest(
    val client_id: String,
    val redirect_uri: String,
    val code: String,
    val client_secret: String? = null,
    val grant_type: String = "authorization_code"
)
