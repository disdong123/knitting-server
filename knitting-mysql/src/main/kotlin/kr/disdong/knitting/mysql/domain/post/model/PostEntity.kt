package kr.disdong.knitting.mysql.domain.post.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import kr.disdong.knitting.mysql.common.model.BaseEntity
import kr.disdong.knitting.mysql.domain.user.model.UserEntity

@Entity(name = "post")
class PostEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity,

    @OneToMany(mappedBy = "post")
    val postUserLikeList: List<PostUserLikeEntity> = listOf(),

    @Column(
        nullable = false,
        unique = false,
        length = 100,
    )
    val title: String,

    @Column(
        nullable = false,
        unique = false,
        length = 1000,
    )
    val content: String,

) : BaseEntity() {

    fun isUserLiked(userId: Long): Boolean {
        return postUserLikeList.map { it.user.id }.contains(userId)
    }
}
