package kr.disdong.knitting.mysql.domain.post.model

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import kr.disdong.knitting.mysql.domain.post.converter.KnittingCategory
import kr.disdong.knitting.mysql.domain.post.converter.KnittingCategoryConverter
import kr.disdong.knitting.mysql.domain.post.converter.KnittingLevel
import kr.disdong.knitting.mysql.domain.post.converter.KnittingLevelConverter
import kr.disdong.knitting.mysql.domain.post.converter.NeedleType
import kr.disdong.knitting.mysql.domain.post.converter.NeedleTypeConverter
import kr.disdong.knitting.mysql.domain.post.converter.PostGatherStatus
import kr.disdong.knitting.mysql.domain.post.converter.PostGatherStatusConverter
import kr.disdong.knitting.mysql.domain.post.converter.PriceType
import kr.disdong.knitting.mysql.domain.post.converter.PriceTypeConverter
import java.time.ZonedDateTime

@Entity(name = "post_gather")
class PostGatherEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne
    @JoinColumn(name = "post_id")
    val post: PostEntity,

    @Column(
        nullable = false,
        unique = false,
        length = 20,
    )
    @Convert(converter = KnittingLevelConverter::class)
    val knittingLevel: KnittingLevel,

    @Column(
        nullable = false,
        unique = false,
        length = 20,
    )
    @Convert(converter = PostGatherStatusConverter::class)
    val status: PostGatherStatus,

    @Column(
        nullable = false,
        unique = false,
        length = 20,
    )
    @Convert(converter = NeedleTypeConverter::class)
    val needleType: NeedleType,

    @Column(
        nullable = false,
        unique = false,
        length = 20,
    )
    @Convert(converter = PriceTypeConverter::class)
    val priceType: PriceType,

    @Column(
        nullable = false,
        unique = false,
        length = 20,
    )
    @Convert(converter = KnittingCategoryConverter::class)
    val category: KnittingCategory,

    @Column(
        nullable = false,
        unique = false,
    )
    val startedAt: ZonedDateTime,

    @Column(
        nullable = false,
        unique = false,
    )
    val endedAt: ZonedDateTime,
)
