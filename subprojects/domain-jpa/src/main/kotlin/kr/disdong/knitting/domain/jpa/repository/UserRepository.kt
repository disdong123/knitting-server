package kr.disdong.knitting.domain.jpa.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.disdong.knitting.domain.jpa.domain.OauthType
import kr.disdong.knitting.domain.jpa.domain.QUserEntity
import kr.disdong.knitting.domain.jpa.domain.QUserOauthMetadataEntity
import kr.disdong.knitting.domain.jpa.domain.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long>, UserRepositoryCustom

interface UserRepositoryCustom {
    fun findByIdAndType(oauthId: String, type: OauthType): UserEntity?
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
}
