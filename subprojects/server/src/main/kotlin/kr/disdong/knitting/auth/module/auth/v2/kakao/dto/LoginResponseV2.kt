package kr.disdong.knitting.auth.module.auth.v2.kakao.dto

import kr.disdong.knitting.common.token.Token
import kr.disdong.knitting.domain.jpa.domain.OauthType

class LoginResponseV2(
    val id: Long,
    val name: String?,
    val phone: String?,
    val oauthType: OauthType,
    val oauthId: String,
    val oauthNickname: String,
    val accessToken: Token
)
