package kr.disdong.knitting.mysql.domain.post.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.ZonedDateTime

@Entity(name = "post_user_like")
data class PostUserLikeEntity(

    @Id
    val id: Long,

    @Column
    val userId: Long,

    @Column
    val postId: Long,

    @Column
    @CreatedDate
    var createdAt: ZonedDateTime = ZonedDateTime.now(),

    @Column
    @LastModifiedDate
    var updatedAt: ZonedDateTime = ZonedDateTime.now(),
)
