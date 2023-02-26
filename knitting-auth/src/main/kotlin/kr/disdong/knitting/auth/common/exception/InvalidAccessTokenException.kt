package kr.disdong.knitting.auth.common.exception

import kr.disdong.knitting.common.token.Token

class InvalidAccessTokenException(token: Token? = null) : AuthException("토큰이 유효하지 않습니다. token: ${token?.value}") {
    override fun getCode(): Int {
        return 401
    }
}
