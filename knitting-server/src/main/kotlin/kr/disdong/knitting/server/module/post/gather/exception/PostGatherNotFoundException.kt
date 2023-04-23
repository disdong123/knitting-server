package kr.disdong.knitting.server.module.post.gather.exception

import kr.disdong.knitting.common.exception.KnittingException

class PostGatherNotFoundException(id: Long) : KnittingException("함뜨 게시글을 찾지 못했습니다. id: $id") {
    override fun getCode(): Int {
        return 404
    }
}

class PostNotFoundException(id: Long) : KnittingException("게시글을 찾지 못했습니다. id: $id") {
    override fun getCode(): Int {
        return 404
    }
}
