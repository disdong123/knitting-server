package kr.disdong.knitting.auth.kakao.exception

import kr.disdong.knitting.common.exception.KnittingException

class UserNotFoundException(id: Long) : KnittingException("유저를 찾지 못했습니다. id: $id") {
    override fun getCode(): Int {
        return 404
    }
}
