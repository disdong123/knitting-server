package kr.disdong.knitting.server.module.post.gather.service

import kr.disdong.knitting.auth.kakao.exception.UserNotFoundException
import kr.disdong.knitting.common.logger.logger
import kr.disdong.knitting.mysql.domain.post.model.PostUserLikeEntity
import kr.disdong.knitting.mysql.domain.post.repository.PostRepository
import kr.disdong.knitting.mysql.domain.post.repository.PostUserLikeRepository
import kr.disdong.knitting.mysql.domain.user.repository.UserRepository
import kr.disdong.knitting.server.module.post.gather.exception.PostNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostUserLikeService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val postUserLikeRepository: PostUserLikeRepository,
) {

    private val logger = logger<PostUserLikeService>()

    @Transactional
    fun like(userId: Long, postId: Long): Long? {
        logger.info("increase(userId: $userId, postId: $postId)")

        val userEntity = userRepository.findByUserId(userId)
            ?: throw UserNotFoundException(userId)

        val postEntity = postRepository.findByPostId(postId)
            ?: throw PostNotFoundException(postId)

        val entity = postUserLikeRepository.findByUserIdAndPostId(userId, postId)

        logger.info("entity: $entity")

        if (entity == null) {
            val newEntity = PostUserLikeEntity(
                id = 1,
                user = userEntity,
                post = postEntity,
            )
            postUserLikeRepository.save(newEntity)
        } else {
            entity.isLike = !entity.isLike
            postUserLikeRepository.save(entity)
        }

        return postUserLikeRepository.countByPostId(postId)
    }
}
