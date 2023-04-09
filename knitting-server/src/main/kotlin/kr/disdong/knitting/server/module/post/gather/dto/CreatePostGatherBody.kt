package kr.disdong.knitting.server.module.post.gather.dto

import kr.disdong.knitting.mysql.domain.post.converter.KnittingCategory
import kr.disdong.knitting.mysql.domain.post.converter.KnittingLevel
import kr.disdong.knitting.mysql.domain.post.converter.NeedleType
import kr.disdong.knitting.mysql.domain.post.converter.PostGatherStatus
import kr.disdong.knitting.mysql.domain.post.converter.PriceType
import org.springframework.format.annotation.DateTimeFormat
import java.time.ZonedDateTime

data class CreatePostGatherBody(
    val title: String,
    val content: String,
    val knittingCategory: KnittingCategory,
    val knittingLevel: KnittingLevel,
    val priceType: PriceType,
    val needleType: NeedleType,
    val postGatherStatus: PostGatherStatus,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val startedAt: ZonedDateTime,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val endedAt: ZonedDateTime,
)
