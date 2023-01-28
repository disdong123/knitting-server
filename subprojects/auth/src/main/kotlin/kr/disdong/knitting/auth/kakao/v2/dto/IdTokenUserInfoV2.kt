package kr.disdong.knitting.auth.kakao.v2.dto

data class IdTokenUserInfoV2(
    val sub: String,
    val name: String?,
    val nickname: String?,
    val picture: String?,
    val email: String?,
    val email_verified: Boolean?,
    val gender: String?,
    val birthdate: String?,
    val phone_number: String?,
    val phone_number_verified: Boolean?,
)
