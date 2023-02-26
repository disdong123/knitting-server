package kr.disdong.knitting.auth

import kr.disdong.knitting.common.CommonApplication
import kr.disdong.knitting.mysql.MysqlApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@Import(MysqlApplication::class, CommonApplication::class)
@SpringBootApplication
class AuthApplication

// fun main(args: Array<String>) {
//     runApplication<AuthApplication>(*args)
// }
