package kr.disdong.knitting.mysql.domain.user.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.disdong.knitting.mysql.domain.user.model.UserOauthMetadataEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserOauthMetadataRepository : JpaRepository<UserOauthMetadataEntity, Long>, UserOauthMetadataRepositoryCustom

interface UserOauthMetadataRepositoryCustom

class UserOauthMetadataRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : UserOauthMetadataRepositoryCustom
