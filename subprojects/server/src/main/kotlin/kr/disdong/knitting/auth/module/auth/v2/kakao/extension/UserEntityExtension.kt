package kr.disdong.knitting.auth.module.auth.v2.kakao.extension

import kr.disdong.knitting.auth.module.auth.v2.kakao.dto.LoginResponseV2
import kr.disdong.knitting.domain.jpa.domain.UserEntity

fun UserEntity.toLoginResponseV2(): LoginResponseV2 {
    return LoginResponseV2(
        id = this.id!!,
        name = this.name,
        phone = this.phone,
        oauthId = this.userOauthMetadata.id,
        oauthNickname = this.userOauthMetadata.nickname,
        oauthType = this.userOauthMetadata.type,
    )
}
