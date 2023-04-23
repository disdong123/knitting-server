package kr.disdong.knitting.mysql.domain.post.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import kr.disdong.knitting.mysql.domain.user.model.UserEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.ZonedDateTime

@Entity(name = "post_user_like")
class PostUserLikeEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: PostEntity,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity,

    // TODO. 어떻게?
    // @Column(name = "post_id")
    // val postId: Long,
    //
    // @Column(name = "user_id")
    // val userId: Long,

    @Column
    var isLike: Boolean = true,

    @Column
    @CreatedDate
    var createdAt: ZonedDateTime = ZonedDateTime.now(),

    @Column
    @LastModifiedDate
    var updatedAt: ZonedDateTime = ZonedDateTime.now(),
)
