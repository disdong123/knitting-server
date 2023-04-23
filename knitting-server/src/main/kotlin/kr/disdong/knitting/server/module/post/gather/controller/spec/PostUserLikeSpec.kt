package kr.disdong.knitting.server.module.post.gather.controller.spec

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.common.dto.KnittingResponse

@Tag(name = "게시글 좋아요", description = "게시글 좋아요 api 입니다.")
interface PostUserLikeSpec {

    @Operation(
        summary = "좋아요",
        description = "게시글 좋아요를 합니다. 이미 좋아요를 눌렀다면 좋아요를 취소합니다.",
    )
    fun like(
        @Parameter(hidden = true)
        claims: AccessTokenClaims,
        postId: Long,
    ): KnittingResponse<Long>
}
