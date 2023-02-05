package kr.disdong.knitting.server.module.auth.kakao.dto

import io.swagger.v3.oas.annotations.media.Schema
import kr.disdong.knitting.common.token.Token
import kr.disdong.knitting.domain.jpa.domain.OauthType

@Schema(description = "login 응답값입니다.")
class LoginResponse(
    val id: Long,
    val name: String?,
    val phone: String?,
    val oauthType: OauthType,
    val oauthId: String,
    val oauthNickname: String,
    @Schema(description = "access token")
    val accessToken: Token
)
