package kr.disdong.knitting.common.token

class JwtHeader(
    val typ: String = "JWT",
    val alg: String = "HS256",
)
