package kr.disdong.knitting.server.module.post.gather.controller

import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.common.annotation.AuthGuard
import kr.disdong.knitting.common.annotation.CurrentUserClaims
import kr.disdong.knitting.server.common.dto.PageParam
import kr.disdong.knitting.server.common.dto.PagedList
import kr.disdong.knitting.server.module.post.gather.controller.spec.PostGatherSpec
import kr.disdong.knitting.server.module.post.gather.dto.CreatePostGatherBody
import kr.disdong.knitting.server.module.post.gather.dto.PostGather
import kr.disdong.knitting.server.module.post.gather.service.PostGatherService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("post/gather")
class PostGatherController(
    private val postGatherService: PostGatherService,
) : PostGatherSpec {

    @GetMapping
    override fun list(pageParam: PageParam): PagedList<PostGather> {
        return postGatherService.list(pageParam)
    }

    @PostMapping
    @AuthGuard
    override fun create(
        @CurrentUserClaims claims: AccessTokenClaims,
        body: CreatePostGatherBody,
    ): PostGather {
        return postGatherService.create(claims.id, body)
    }
}
