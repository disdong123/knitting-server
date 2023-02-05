package kr.disdong.knitting.server.module.health.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    val logger = LoggerFactory.getLogger(TestController::class.java)

    @GetMapping("/health")
    fun health(): String {
        return "knitting server! 워니쨩"
    }
}