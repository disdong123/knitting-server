
package kr.disdong.domain.service

import PostDoImpl
import UnsavedPostDoImpl
import kr.disdong.domain.domain.PostEntityFixture
import kr.disdong.domain.repository.PostDoRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class PostServiceTest {
    // @Autowired
    @InjectMocks
    private lateinit var sut: PostService

    @Mock
    private lateinit var postDoRepository: PostDoRepository

    @Nested
    inner class `create 메소드는` {

        private val title = "title"

        @Test
        fun `post 를 생성한다`() {
            BDDMockito.given(postDoRepository.save(UnsavedPostDoImpl(title = title)))
                .willReturn(PostDoImpl(PostEntityFixture.any(title)))

            val response = sut.save(title)

            assertEquals(response.title, title)
        }
    }
}
