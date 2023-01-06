package kr.disdong.knitting.server

import kr.disdong.domain.DomainApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(DomainApplication::class) // scanBasePackages 설정은 지워도 됩니다.
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}
