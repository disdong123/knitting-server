package kr.disdong.knitting.mysql.domain.file.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.disdong.knitting.mysql.domain.file.model.FileEntity
import kr.disdong.knitting.mysql.domain.file.model.QFileEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FileRepository : JpaRepository<FileEntity, Long>, FileCustomRepository

interface FileCustomRepository {
    fun findByIdAndUserId(id: Long, userId: Long): FileEntity?

    fun findAllByIdsAndUserId(ids: List<Long>, userId: Long): List<FileEntity>
}

class FileRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : FileCustomRepository {

    override fun findByIdAndUserId(id: Long, userId: Long): FileEntity? {
        return jpaQueryFactory.selectFrom(QFileEntity.fileEntity)
            .where(
                QFileEntity.fileEntity.id.eq(id)
                    .and(QFileEntity.fileEntity.user.id.eq(userId))
            )
            .fetchOne()
    }

    override fun findAllByIdsAndUserId(ids: List<Long>, userId: Long): List<FileEntity> {
        return jpaQueryFactory.selectFrom(QFileEntity.fileEntity)
            .where(
                QFileEntity.fileEntity.id.`in`(ids)
                    .and(QFileEntity.fileEntity.user.id.eq(userId))
            )
            .fetch()
    }
}
