package kr.disdong.knitting.server

import kr.disdong.knitting.common.CommonApplication
import kr.disdong.knitting.mysql.MysqlApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(MysqlApplication::class, CommonApplication::class) // scanBasePackages 설정은 지워도 됩니다.
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}
