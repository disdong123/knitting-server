package kr.disdong.knitting.storage.common.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import kr.disdong.knitting.common.logger.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class S3Service(
    @Value("\${cloud.s3.bucket}")
    private val bucketName: String,
    private val amazonS3Client: AmazonS3Client,
) {

    private val logger = logger<S3Service>()

    fun upload(file: MultipartFile): S3Response {
        logger.info("upload(file: ${file.name})")

        val objectMetaData = ObjectMetadata()
        objectMetaData.contentType = file.contentType
        objectMetaData.contentLength = file.size

        val key = UUID.randomUUID()

        // S3에 업로드
        amazonS3Client.putObject(
            PutObjectRequest(bucketName, key.toString(), file.inputStream, objectMetaData)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        )

        return S3Response(
            key,
            amazonS3Client.getUrl(bucketName, key.toString()).toString()
        )
    }
}

class S3Response(
    val key: UUID,
    val url: String,
)
