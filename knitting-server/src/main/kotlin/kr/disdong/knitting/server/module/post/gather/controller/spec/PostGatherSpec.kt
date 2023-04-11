package kr.disdong.knitting.server.module.post.gather.controller.spec

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.common.annotation.CurrentUserClaims
import kr.disdong.knitting.server.common.dto.PageParam
import kr.disdong.knitting.server.common.dto.PagedList
import kr.disdong.knitting.server.module.post.gather.dto.CreatePostGatherBody
import kr.disdong.knitting.server.module.post.gather.dto.PostGather

@Tag(name = "함뜨 게시판", description = "함뜨 게시판 api 입니다.")
interface PostGatherSpec {

    @Operation(
        summary = "함뜨 게시글 리스트",
        parameters = [
            Parameter(
                name = "page",
                description = "페이지",
                required = true,
                example = "1",
            ),
            Parameter(
                name = "size",
                description = "페이지 사이즈",
                required = true,
                example = "10",
            ),
        ],
        responses = []
    )
    fun list(pageParam: PageParam): PagedList<PostGather>

    @Operation(
        summary = "함뜨 게시글 생성",
    )
    fun create(
        @CurrentUserClaims claims: AccessTokenClaims,
        body: CreatePostGatherBody,
    ): PostGather

    @Operation(
        summary = "함뜨 게시글 상세조회",
    )
    fun getById(
        id: Long
    ): PostGather
}
