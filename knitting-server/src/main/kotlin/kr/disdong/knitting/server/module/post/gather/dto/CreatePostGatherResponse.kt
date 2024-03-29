package kr.disdong.knitting.server.module.post.gather.dto

import kr.disdong.knitting.mysql.domain.post.converter.KnittingCategory
import kr.disdong.knitting.mysql.domain.post.converter.KnittingLevel
import kr.disdong.knitting.mysql.domain.post.converter.NeedleType
import kr.disdong.knitting.mysql.domain.post.converter.PostGatherStatus
import kr.disdong.knitting.mysql.domain.post.converter.PriceType
import kr.disdong.knitting.mysql.domain.post.model.PostGatherEntity
import java.time.ZonedDateTime

data class PostGather(
    val postId: Long,
    val title: String,
    val knittingCategory: KnittingCategory,
    val knittingLevel: KnittingLevel,
    val priceType: PriceType,
    val needleType: NeedleType,
    val postGatherStatus: PostGatherStatus,
    val isLike: Boolean,
    val likeCount: Long,
    val startedAt: ZonedDateTime,
    val endedAt: ZonedDateTime,
    val postGatherDetail: PostGatherDetail? = null,
) {
    companion object {

        fun of(entity: PostGatherEntity, isLike: Boolean): PostGather {
            return PostGather(
                postId = entity.post.id,
                title = entity.post.title,
                knittingCategory = entity.category,
                knittingLevel = entity.knittingLevel,
                priceType = entity.priceType,
                needleType = entity.needleType,
                postGatherStatus = entity.status,
                isLike = isLike,
                likeCount = entity.post.postUserLikeList.count().toLong(),
                startedAt = entity.startedAt,
                endedAt = entity.endedAt,
                postGatherDetail = PostGatherDetail(
                    content = entity.post.content,
                ),
            )
        }

        fun of(entities: List<PostGatherEntity>, isListList: List<Boolean>): List<PostGather> = entities.mapIndexed { index, it -> of(it, isListList[index]) }
    }
}

data class PostGatherDetail(
    val content: String,
)
