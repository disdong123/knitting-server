package kr.disdong.knitting.auth.kakao.dto

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.swagger.v3.oas.annotations.media.Schema
import kr.disdong.knitting.common.token.Token
import kr.disdong.knitting.common.token.TokenSerializer
import kr.disdong.knitting.domain.jpa.domain.OauthType

@Schema(description = "login 응답값입니다.")
class LoginResponse(
    val id: Long,
    val name: String?,
    val phone: String?,
    val oauthType: OauthType,
    val oauthId: String,
    val oauthNickname: String,
    @Schema(description = "access token", type = "String")
    @JsonSerialize(using = TokenSerializer::class)
    val accessToken: Token
)
