package kr.disdong.knitting.storage.common.service

import kr.disdong.knitting.common.logger.logger
import kr.disdong.knitting.mysql.domain.file.model.FileEntity
import kr.disdong.knitting.mysql.domain.file.repository.FileRepository
import kr.disdong.knitting.mysql.domain.user.repository.UserRepository
import kr.disdong.knitting.storage.common.dto.FileResponse
import kr.disdong.knitting.storage.common.exception.FileNotFoundException
import kr.disdong.knitting.storage.common.exception.FilesNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class FileService(
    private val s3Service: S3Service,
    private val fileRepository: FileRepository,
    private val userRepository: UserRepository,
) {

    private val logger = logger<FileService>()

    @Transactional
    fun upload(userId: Long, file: MultipartFile): FileResponse {
        logger.info("upload(userId: $userId, file: ${file.name})")

        if (file.isEmpty) {
            throw IllegalArgumentException("File is empty")
        }

        val response = s3Service.upload(file)
        val userEntity = userRepository.findByUserId(userId)

        val entity = FileEntity(
            user = userEntity!!,
            originalName = file.originalFilename,
            name = file.name,
            cloudKey = response.key.toString(),
            cloudUrl = response.url,
            size = file.size,
            mime = file.contentType,
        )

        logger.info("entity: $entity")

        return FileResponse.from(fileRepository.save(entity))
    }

    @Transactional(readOnly = true)
    fun getById(userId: Long, id: Long): FileResponse {
        val entity = fileRepository.findByIdAndUserId(id, userId) ?: throw FileNotFoundException(id)

        return FileResponse.from(entity)
    }

    @Transactional(readOnly = true)
    fun getByIds(userId: Long, ids: List<Long>): List<FileResponse> {
        val entities = fileRepository.findAllByIdsAndUserId(ids, userId)

        logger.info("entities: ${entities.size}")

        if (entities.size != ids.size) {
            throw FilesNotFoundException(ids)
        }

        return entities.map {
            FileResponse.from(it)
        }
    }
}
