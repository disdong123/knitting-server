package kr.disdong.knitting.mysql.domain.comment.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import kr.disdong.knitting.mysql.common.model.BaseEntity
import kr.disdong.knitting.mysql.domain.user.model.UserEntity

@Entity(name = "comment")
class CommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(targetEntity = UserEntity::class)
    @JoinColumn(name = "user_id")
    val user: UserEntity,

    @Column
    val content: String,

) : BaseEntity()
