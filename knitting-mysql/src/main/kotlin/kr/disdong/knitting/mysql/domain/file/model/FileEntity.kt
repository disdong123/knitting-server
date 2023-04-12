package kr.disdong.knitting.mysql.domain.file.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import kr.disdong.knitting.mysql.common.model.BaseEntity
import kr.disdong.knitting.mysql.domain.user.model.UserEntity

@Entity(name = "file")
class FileEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity,

    @Column(
        nullable = false,
        unique = false,
        length = 100,
    )
    val name: String,

    @Column(
        nullable = false,
        unique = false,
        length = 100,
    )
    val originalName: String? = null,

    @Column(
        nullable = false,
        unique = false,
        length = 100,
    )
    val size: Long,

    @Column(
        nullable = false,
        unique = false,
        length = 100,
    )
    val mime: String? = null,

    @Column(
        nullable = false,
        unique = true,
        length = 100,
    )
    val cloudKey: String,

    @Column(
        nullable = false,
        unique = true,
        length = 100,
    )
    val cloudUrl: String,
) : BaseEntity()
