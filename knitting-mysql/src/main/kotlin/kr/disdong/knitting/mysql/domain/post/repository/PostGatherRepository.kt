package kr.disdong.knitting.mysql.domain.post.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.disdong.knitting.mysql.domain.post.model.PostGatherEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostGatherRepository : JpaRepository<PostGatherEntity, Long>, PostGatherCustomRepository
interface PostGatherCustomRepository

class PostGatherCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : PostGatherCustomRepository
