package kr.disdong.knitting.mysql.domain.post.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.disdong.knitting.mysql.domain.post.model.PostGatherEntity
import kr.disdong.knitting.mysql.domain.post.model.QPostEntity
import kr.disdong.knitting.mysql.domain.post.model.QPostGatherEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostGatherRepository : JpaRepository<PostGatherEntity, Long>, PostGatherCustomRepository
interface PostGatherCustomRepository {
    fun findWithPostById(id: Long): PostGatherEntity?
}
