package kr.disdong.knitting.auth.kakao.v2.dto

import com.google.gson.Gson

data class IdTokenV2(
    val iss: String,
    val aud: String,
    val sub: String,
    val iat: Int,
    val exp: Int,
    val auth_time: Int,
    val nickname: String,
) {
    companion object {

        fun of(str: String): IdTokenV2 {
            return Gson().fromJson(str, IdTokenV2::class.java)
        }
    }
}
