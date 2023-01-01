package kr.disdong.gradle.multi.module.template.core.mysql.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "post")
class PostEntity(
    @Id
    var id: Long?
)
