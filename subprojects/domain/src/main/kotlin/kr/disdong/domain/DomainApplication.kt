package kr.disdong.domain

import kr.disdong.knitting.domain.jpa.DomainJpaApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(DomainJpaApplication::class)
class DomainApplication
