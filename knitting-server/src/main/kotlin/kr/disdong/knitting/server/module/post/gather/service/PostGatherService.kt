package kr.disdong.knitting.server.module.post.gather.service

import kr.disdong.knitting.auth.kakao.exception.UserNotFoundException
import kr.disdong.knitting.common.logger.logger
import kr.disdong.knitting.mysql.domain.post.model.PostEntity
import kr.disdong.knitting.mysql.domain.post.model.PostGatherEntity
import kr.disdong.knitting.mysql.domain.post.repository.PostGatherRepository
import kr.disdong.knitting.mysql.domain.post.repository.PostRepository
import kr.disdong.knitting.mysql.domain.user.repository.UserRepository
import kr.disdong.knitting.server.common.dto.PageParam
import kr.disdong.knitting.server.common.dto.PagedList
import kr.disdong.knitting.server.module.post.gather.dto.CreatePostGatherBody
import kr.disdong.knitting.server.module.post.gather.dto.PostGather
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostGatherService(
    private val postGatherRepository: PostGatherRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) {

    private val logger = logger<PostGatherService>()

    @Transactional(readOnly = true)
    fun list(pageParam: PageParam): PagedList<PostGather> {
        val entities = postGatherRepository.findAll()

        return PagedList(
            page = pageParam.page,
            size = pageParam.size,
            total = entities.size,
            PostGather.of(entities)
        )
    }

    @Transactional
    fun create(userId: Long, body: CreatePostGatherBody): PostGather {
        logger.info("create(userId=$userId, body=$body)")

        val userEntity = userRepository.findByUserId(userId)
            ?: throw UserNotFoundException(userId)

        logger.info("userEntity=$userEntity")

        val post = PostEntity(
            user = userEntity,
            title = body.title,
            content = body.content,
        )

        val postEntity = postRepository.save(post)

        logger.info("postEntity=$postEntity")

        val postGatherEntity = PostGatherEntity(
            post = postEntity,
            category = body.knittingCategory,
            knittingLevel = body.knittingLevel,
            priceType = body.priceType,
            needleType = body.needleType,
            status = body.postGatherStatus,
            startedAt = body.startedAt,
            endedAt = body.endedAt,
        )

        logger.info("postGatherEntity=$postGatherEntity")

        println(postGatherEntity)

        return PostGather.of(postGatherRepository.save(postGatherEntity))
    }
}
