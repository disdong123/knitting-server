package kr.disdong.knitting.mysql.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.disdong.knitting.mysql.domain.UserOauthMetadataEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserOauthMetadataRepository : JpaRepository<UserOauthMetadataEntity, Long>, UserOauthMetadataRepositoryCustom

interface UserOauthMetadataRepositoryCustom

class UserOauthMetadataRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : UserOauthMetadataRepositoryCustom
