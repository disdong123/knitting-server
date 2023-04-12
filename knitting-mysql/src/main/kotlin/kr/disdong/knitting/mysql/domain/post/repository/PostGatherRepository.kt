package kr.disdong.knitting.mysql.domain.post.repository

import kr.disdong.knitting.mysql.domain.post.model.PostGatherEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostGatherRepository : JpaRepository<PostGatherEntity, Long>, PostGatherCustomRepository
interface PostGatherCustomRepository {
    fun findWithPostById(id: Long): PostGatherEntity?
}
