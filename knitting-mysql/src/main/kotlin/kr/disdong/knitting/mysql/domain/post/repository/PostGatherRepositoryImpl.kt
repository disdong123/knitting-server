package kr.disdong.knitting.mysql.domain.post.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.disdong.knitting.mysql.domain.post.model.PostGatherEntity
import kr.disdong.knitting.mysql.domain.post.model.QPostEntity
import kr.disdong.knitting.mysql.domain.post.model.QPostGatherEntity

class PostGatherRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : PostGatherCustomRepository {

    private val postGather = QPostGatherEntity.postGatherEntity
    private val post = QPostEntity.postEntity

    override fun findWithPostById(id: Long): PostGatherEntity? {
        return jpaQueryFactory.selectFrom(postGather)
            .join(postGather.post, post)
            .where(
                postGather.id.eq(id)
                    .and(post.isDeleted.eq(false))
                    .and(
                        post.isUsed.eq(true)
                    )
            )
            .fetchOne()
    }
}
