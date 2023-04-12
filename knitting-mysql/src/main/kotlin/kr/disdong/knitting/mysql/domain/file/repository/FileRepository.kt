package kr.disdong.knitting.mysql.domain.file.repository

import kr.disdong.knitting.mysql.domain.file.model.FileEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FileRepository : JpaRepository<FileEntity, Long>
