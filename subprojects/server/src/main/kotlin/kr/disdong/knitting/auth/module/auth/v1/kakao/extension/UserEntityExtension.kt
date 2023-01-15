package kr.disdong.knitting.auth.module.auth.v1.kakao.extension

import kr.disdong.knitting.auth.module.auth.v1.kakao.dto.LoginResponse
import kr.disdong.knitting.domain.jpa.domain.UserEntity


fun UserEntity.toLoginResponse(): LoginResponse {
    return LoginResponse(
        id = this.id!!,
        name = this.name,
        phone = this.phone,
        oauthId = this.userOauthMetadata.id,
        oauthNickname = this.userOauthMetadata.nickname,
        oauthType = this.userOauthMetadata.type,
    )
}
