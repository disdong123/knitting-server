package kr.disdong.knitting.mysql.domain.post.repository

import kr.disdong.knitting.mysql.domain.post.model.PostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<PostEntity, Long>, PostCustomRepository

interface PostCustomRepository
