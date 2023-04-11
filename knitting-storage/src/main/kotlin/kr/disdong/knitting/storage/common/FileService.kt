package kr.disdong.knitting.storage.common

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService(
    private val s3Service: S3Service,
) {

    fun upload(file: MultipartFile) {
        if (file.isEmpty) {
            throw IllegalArgumentException("File is empty")
        }


    }
}
