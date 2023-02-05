package kr.disdong.knitting.server.module.auth.kakao.dto

import kr.disdong.knitting.common.token.Token
import kr.disdong.knitting.domain.jpa.domain.OauthType

class LoginResponse(
    val id: Long,
    val name: String?,
    val phone: String?,
    val oauthType: OauthType,
    val oauthId: String,
    val oauthNickname: String,
    val accessToken: Token
)
