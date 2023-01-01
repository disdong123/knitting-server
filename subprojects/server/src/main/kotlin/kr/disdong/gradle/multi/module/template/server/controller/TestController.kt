package kr.disdong.gradle.multi.module.template.server.controller

import kr.disdong.gradle.multi.module.template.core.mysql.repository.PostRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val postRepository: PostRepository
) {

    @GetMapping("/t1")
    fun a() {
        println(postRepository.findAll()[0].id)
        println(postRepository.hello()[0].id)
    }
}
