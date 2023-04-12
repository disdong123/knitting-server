package kr.disdong.knitting.storage.common.dto

import kr.disdong.knitting.mysql.domain.file.model.FileEntity

class FileResponse(
    val id: Long,
    val name: String,
    val originalName: String? = null,
    val size: Long,
    val mime: String? = null,
    val s3Url: String,
) {
    companion object {
        fun from(entity: FileEntity) = FileResponse(
            id = entity.id,
            name = entity.name,
            originalName = entity.originalName,
            size = entity.size,
            mime = entity.mime,
            s3Url = entity.cloudUrl,
        )
    }
}
