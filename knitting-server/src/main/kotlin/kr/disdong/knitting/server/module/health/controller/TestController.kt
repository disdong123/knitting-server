package kr.disdong.knitting.server.module.health.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "테스트", description = "테스트용 api 입니다.")
class TestController {

    val logger = LoggerFactory.getLogger(TestController::class.java)

    @GetMapping("/health")
    @Operation(summary = "일반 테스트 api")
    fun health(): String {
        println("health()")
        return "knitting server! 워니쨩"
    }

    // @GetMapping("/health/auth")
    // @AuthGuard
    // @Operation(summary = "인증 테스트 api")
    // fun authHealth(
    //     @CurrentUserClaims
    //     @Parameter(name = "value", hidden = true)
    //     value: AccessTokenClaims?
    // ): String {
    //     return "knitting server! 워니쨩: $value"
    // }
}
