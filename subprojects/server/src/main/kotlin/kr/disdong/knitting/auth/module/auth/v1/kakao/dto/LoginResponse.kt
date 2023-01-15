package kr.disdong.knitting.auth.module.auth.v1.kakao.dto

import kr.disdong.knitting.domain.jpa.domain.OauthType

class LoginResponse(
    val id: Long,
    val name: String?,
    val phone: String?,
    val oauthType: OauthType,
    val oauthId: String,
    val oauthNickname: String,
)
