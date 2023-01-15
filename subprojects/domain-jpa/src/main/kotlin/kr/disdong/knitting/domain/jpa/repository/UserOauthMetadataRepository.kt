package kr.disdong.knitting.domain.jpa.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.disdong.knitting.domain.jpa.domain.*
import org.springframework.data.jpa.repository.JpaRepository

interface UserOauthMetadataRepository : JpaRepository<UserOauthMetadata, Long>, UserOauthMetadataRepositoryCustom

interface UserOauthMetadataRepositoryCustom {
}

class UserOauthMetadataRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : UserOauthMetadataRepositoryCustom {
}
