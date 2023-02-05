package kr.disdong.knitting.server.module.auth.kakao.extension

import kr.disdong.knitting.common.token.Token
import kr.disdong.knitting.domain.jpa.domain.UserEntity
import kr.disdong.knitting.server.module.auth.kakao.dto.LoginResponse

fun UserEntity.toLoginResponse(accessToken: Token): LoginResponse {
    return LoginResponse(
        id = this.id!!,
        name = this.name,
        phone = this.phone,
        oauthId = this.userOauthMetadata.id,
        oauthNickname = this.userOauthMetadata.nickname,
        oauthType = this.userOauthMetadata.type,
        accessToken = accessToken
    )
}
