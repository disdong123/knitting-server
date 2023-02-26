package kr.disdong.knitting.server

import kr.disdong.knitting.auth.AuthApplication
import kr.disdong.knitting.common.CommonApplication
import kr.disdong.knitting.domain.jpa.DomainJpaApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(DomainJpaApplication::class, CommonApplication::class, AuthApplication::class) // scanBasePackages 설정은 지워도 됩니다.
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}
