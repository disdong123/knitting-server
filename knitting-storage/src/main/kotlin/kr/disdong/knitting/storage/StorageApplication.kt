package kr.disdong.knitting.storage

import kr.disdong.knitting.mysql.MysqlApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(MysqlApplication::class)
class StorageApplication
