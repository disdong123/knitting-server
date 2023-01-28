package kr.disdong.knitting.auth.kakao.v1.dto

import com.google.gson.Gson

data class IdToken(
    val iss: String,
    val aud: String,
    val sub: String,
    val iat: Int,
    val exp: Int,
    val auth_time: Int,
    val nickname: String,
) {
    companion object {

        fun of(str: String): IdToken {
            return Gson().fromJson(str, IdToken::class.java)
        }
    }
}
