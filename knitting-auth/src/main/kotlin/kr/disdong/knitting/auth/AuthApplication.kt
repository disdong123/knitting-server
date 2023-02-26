package kr.disdong.knitting.auth

import kr.disdong.knitting.common.CommonApplication
import kr.disdong.knitting.domain.jpa.DomainJpaApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@Import(DomainJpaApplication::class, CommonApplication::class)
@SpringBootApplication
class AuthApplication
