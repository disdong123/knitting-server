package kr.disdong.knitting.mysql.domain.post.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.disdong.knitting.mysql.domain.post.model.PostUserLikeEntity
import kr.disdong.knitting.mysql.domain.post.model.QPostUserLikeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostUserLikeRepository : JpaRepository<PostUserLikeEntity, Long>, PostUserLikeCustomRepository

interface PostUserLikeCustomRepository {
    fun countByPostId(postId: Long): Long?
    fun findByUserIdAndPostId(userId: Long, postId: Long): PostUserLikeEntity?
}

class PostUserLikeRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : PostUserLikeCustomRepository {

    private val postUserLike = QPostUserLikeEntity.postUserLikeEntity

    override fun countByPostId(postId: Long): Long? {
        return jpaQueryFactory.select(postUserLike.count())
            .from(postUserLike)
            .where(
                postUserLike.post.id.eq(postId)
                    .and(postUserLike.isLike.eq(true))
            )
            .fetchOne()
    }

    override fun findByUserIdAndPostId(userId: Long, postId: Long): PostUserLikeEntity? {
        return jpaQueryFactory.selectFrom(postUserLike)
            .where(
                postUserLike.user.id.eq(userId)
                    .and(postUserLike.post.id.eq(postId))
            )
            .fetchOne()
    }
}
