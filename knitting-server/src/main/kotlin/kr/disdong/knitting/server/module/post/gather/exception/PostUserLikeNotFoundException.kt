package kr.disdong.knitting.server.module.post.gather.exception

import kr.disdong.knitting.common.exception.KnittingException

class PostUserLikeNotFoundException : KnittingException("좋아요 하지 않은 게시글입니다.") {
    override fun getCode(): Int {
        return 404
    }
}
