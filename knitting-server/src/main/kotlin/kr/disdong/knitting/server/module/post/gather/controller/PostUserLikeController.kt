package kr.disdong.knitting.server.module.post.gather.controller

import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.common.annotation.AuthGuard
import kr.disdong.knitting.common.annotation.CurrentUserClaims
import kr.disdong.knitting.common.dto.KnittingResponse
import kr.disdong.knitting.server.module.post.gather.controller.spec.PostUserLikeSpec
import kr.disdong.knitting.server.module.post.gather.service.PostUserLikeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class PostUserLikeController(
    private val postUserLikeService: PostUserLikeService,
) : PostUserLikeSpec {

    @PatchMapping("/posts/{postId}/like")
    @AuthGuard
    @ResponseStatus(HttpStatus.CREATED)
    override fun like(
        @CurrentUserClaims claims: AccessTokenClaims,
        @PathVariable postId: Long,
    ): KnittingResponse<Long> {
        return KnittingResponse.of(postUserLikeService.like(claims.id, postId))
    }
}
