package kr.disdong.knitting.server.controller

import PostDo
import kr.disdong.domain.repository.PostDoRepository
import kr.disdong.domain.service.PostService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val postDoRepository: PostDoRepository,
    private val postService: PostService
) {

    val logger = LoggerFactory.getLogger(TestController::class.java)

    @GetMapping("/health")
    fun health(): String {
        logger.info("1 ${postDoRepository.findAll()[0].id}")
        logger.info("2 ${postDoRepository.hello()[0].id}")
        println(postDoRepository.findAll()[0].id)
        println(postDoRepository.hello()[0].id)
        return "knitting server! 워니쨩"
    }

    @PostMapping("/post")
    fun createPost(@RequestBody title: CreatePostBody): PostDo {
        logger.info("title: ${title.title}")
        return postService.save(title.title)
    }
}

class CreatePostBody(
    var title: String
)
