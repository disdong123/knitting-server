package kr.disdong.knitting.domain.jpa.domain

import jakarta.persistence.*
import kr.disdong.knitting.common.token.Token
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity(name = "user")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.PERSIST])
    var userOauthMetadata: UserOauthMetadataEntity,

    @Column(
        nullable = true,
        unique = false,
        length = 100,
    )
    var name: String? = null,

    @Column(
        nullable = true,
        unique = false,
        length = 20,
    )
    var phone: String? = null,

    @Column
    @ColumnDefault("true")
    var isUsed: Boolean? = true,

    @Column
    @ColumnDefault("false")
    var isDeleted: Boolean? = false,

    @Column
    @CreatedDate
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column
    @LastModifiedDate
    var updatedAt: LocalDateTime? = LocalDateTime.now(),
) {

    /**
     * 로그인 시, 유저의 토큰을 저장합니다.
     * @param accessToken
     * @param refreshToken
     */
    fun setTokens(accessToken: Token, refreshToken: Token) {
        userOauthMetadata.accessToken = accessToken
        userOauthMetadata.refreshToken = refreshToken
    }

    /**
     * 로그아웃 시, 유저의 모든 토큰을 제거합니다.
     */
    fun removeTokens() {
        userOauthMetadata.accessToken = null
        userOauthMetadata.refreshToken = null
    }
}
