package kr.disdong.knitting.mysql.domain.post.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.disdong.knitting.mysql.domain.post.model.PostEntity
import kr.disdong.knitting.mysql.domain.post.model.QPostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<PostEntity, Long>, PostCustomRepository

interface PostCustomRepository {
    fun findByPostId(id: Long): PostEntity?
}

class PostRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : PostCustomRepository {
    private val post = QPostEntity.postEntity

    override fun findByPostId(id: Long): PostEntity? {
        return jpaQueryFactory.selectFrom(post)
            .where(
                post.id.eq(id)
                    .and(post.isDeleted.eq(false))
                    .and(
                        post.isUsed.eq(true)
                    )
            )
            .fetchOne()
    }
}
