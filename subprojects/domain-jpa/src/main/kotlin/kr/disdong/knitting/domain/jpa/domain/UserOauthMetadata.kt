package kr.disdong.knitting.domain.jpa.domain

import jakarta.persistence.*
import org.hibernate.annotations.Comment

enum class OauthType(val value: String) {
    KAKAO("kakao");
}

/**
 * oauth 로그인 시 알 수 있는 정보입니다.
 */
@Entity(name = "user_oauth_metadata")
class UserOauthMetadata(
    // oauth token 에서 유저를 구분할 수 있는 유일값입니다.
    // kakao 는 sub 값 입니다.
    @Comment("oauth token 에서 유저를 구분할 수 있는 유일값")
    @Id
    var id: String,

    @Comment("각 서비스에서 사용하는 nickname")
    @Column(
        nullable = false,
        unique = false,
        length = 200,
    )
    var nickname: String,

    @Comment("oauth 타입")
    @Enumerated(value = EnumType.STRING)
    @Column(
        nullable = false,
        unique = false,
        length = 10,
    )
    var type: OauthType
)
