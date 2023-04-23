package kr.disdong.knitting.server.module.post.gather.controller

import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.common.annotation.AuthGuard
import kr.disdong.knitting.common.annotation.CurrentUserClaims
import kr.disdong.knitting.common.dto.KnittingResponse
import kr.disdong.knitting.server.common.dto.PageParam
import kr.disdong.knitting.server.common.dto.PagedList
import kr.disdong.knitting.server.module.post.gather.controller.spec.PostGatherSpec
import kr.disdong.knitting.server.module.post.gather.dto.CreatePostGatherBody
import kr.disdong.knitting.server.module.post.gather.dto.PostGather
import kr.disdong.knitting.server.module.post.gather.service.PostGatherService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("post/gather")
class PostGatherController(
    private val postGatherService: PostGatherService,
) : PostGatherSpec {

    @GetMapping
    @AuthGuard
    override fun list(
        @CurrentUserClaims claims: AccessTokenClaims,
        pageParam: PageParam,
    ): KnittingResponse<PagedList<PostGather>> {
        return KnittingResponse.of(postGatherService.list(pageParam, claims.id))
    }

    @GetMapping("{id}")
    @AuthGuard
    override fun getById(
        @CurrentUserClaims claims: AccessTokenClaims,
        id: Long,
    ): KnittingResponse<PostGather> {
        return KnittingResponse.of(postGatherService.getById(id, claims.id))
    }

    @PostMapping
    @AuthGuard
    @ResponseStatus(HttpStatus.CREATED)
    override fun create(
        @CurrentUserClaims claims: AccessTokenClaims,
        body: CreatePostGatherBody,
    ): KnittingResponse<PostGather> {
        return KnittingResponse.of(postGatherService.create(claims.id, body))
    }
}
