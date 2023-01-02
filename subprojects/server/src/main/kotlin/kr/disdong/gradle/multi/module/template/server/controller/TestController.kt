package kr.disdong.gradle.multi.module.template.server.controller

import kr.disdong.gradle.multi.module.template.core.mysql.repository.PostRepository
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val postRepository: PostRepository
) {

    val logger = LoggerFactory.getLogger(TestController::class.java)

    @GetMapping("/health")
    fun health(): String {
        logger.info("1 ${postRepository.findAll()[0].id}")
        logger.info("2 ${postRepository.hello()[0].id}")
        println(postRepository.findAll()[0].id)
        println(postRepository.hello()[0].id)
        return "knitting server! 워니쨩"
    }
}
