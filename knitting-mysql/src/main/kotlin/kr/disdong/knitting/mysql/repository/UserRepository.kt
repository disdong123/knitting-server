package kr.disdong.knitting.mysql.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.disdong.knitting.common.token.Token
import kr.disdong.knitting.mysql.domain.OauthType
import kr.disdong.knitting.mysql.domain.QUserEntity
import kr.disdong.knitting.mysql.domain.QUserOauthMetadataEntity
import kr.disdong.knitting.mysql.domain.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long>, UserRepositoryCustom

interface UserRepositoryCustom {
    fun findByIdAndType(oauthId: String, type: OauthType): UserEntity?

    fun findByAccessToken(token: Token): UserEntity?
    fun findByPhone(token: String): UserEntity?
    fun findByUserId(id: Long): UserEntity?
}

class UserRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : UserRepositoryCustom {

    private val userEntity = QUserEntity.userEntity
    private val userOauthMetadata = QUserOauthMetadataEntity.userOauthMetadataEntity

    override fun findByIdAndType(oauthId: String, type: OauthType): UserEntity? {
        return jpaQueryFactory
            .selectFrom(userEntity)
            .innerJoin(userEntity.userOauthMetadata, userOauthMetadata)
            .where(
                userEntity.userOauthMetadata.id.eq(oauthId)
                    .and(userEntity.userOauthMetadata.type.eq(type))
            )
            .fetchOne()
    }

    override fun findByAccessToken(token: Token): UserEntity? {
        return jpaQueryFactory
            .selectFrom(userEntity)
            .innerJoin(userEntity.userOauthMetadata, userOauthMetadata)
            .where(
                userOauthMetadata.accessToken.eq(token)
            )
            .fetchOne()
    }

    override fun findByPhone(phone: String): UserEntity? {
        return jpaQueryFactory
            .selectFrom(userEntity)
            .where(
                userEntity.phone.eq(phone)
            )
            .fetchOne()
    }

    override fun findByUserId(id: Long): UserEntity? {
        return jpaQueryFactory
            .selectFrom(userEntity)
            .where(
                userEntity.id.eq(id)
            )
            .fetchOne()
    }
}
