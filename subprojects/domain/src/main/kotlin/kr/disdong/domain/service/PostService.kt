package kr.disdong.domain.service

import PostDoImpl
import UnsavedPostDoImpl
import kr.disdong.domain.repository.PostDoRepository
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postDoRepository: PostDoRepository,
) {

    /**
     *
     * @param title
     */
    fun save(title: String): PostDoImpl {
        val post = UnsavedPostDoImpl(title = title)

        return postDoRepository.save(post)
    }
}
