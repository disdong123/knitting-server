package kr.disdong.knitting.auth.common.exception

class AuthorizationCodeAccessDeniedException() : AuthException("Resource server 로 부터 토큰을 받지 못했습니다.") {

    override fun getCode(): Int {
        return 403
    }
}
