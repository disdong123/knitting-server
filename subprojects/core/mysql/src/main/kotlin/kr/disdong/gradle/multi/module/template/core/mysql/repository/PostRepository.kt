package kr.disdong.gradle.multi.module.template.core.mysql.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.disdong.gradle.multi.module.template.core.mysql.domain.PostEntity
import kr.disdong.gradle.multi.module.template.core.mysql.domain.QPostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<PostEntity, Long>, PostRepositoryCustom

interface PostRepositoryCustom {
    fun hello(): MutableList<PostEntity>
}

class PostRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : PostRepositoryCustom {

    override fun hello(): MutableList<PostEntity> {

        return jpaQueryFactory.selectFrom(QPostEntity.postEntity)
            .fetch()
    }
}
