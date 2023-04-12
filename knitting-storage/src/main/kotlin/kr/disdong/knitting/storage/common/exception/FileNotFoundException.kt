package kr.disdong.knitting.storage.common.exception

import kr.disdong.knitting.common.exception.KnittingException

class FileNotFoundException(id: Long) : KnittingException("파일을 찾지 못했습니다. id: $id") {
    override fun getCode(): Int {
        return 404
    }
}

class FilesNotFoundException(ids: List<Long>) : KnittingException("파일을 찾지 못했습니다. ids: $ids") {
    override fun getCode(): Int {
        return 404
    }
}
