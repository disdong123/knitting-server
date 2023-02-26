package kr.disdong.knitting.server.module.auth.kakao.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.mockk
import kr.disdong.knitting.auth.kakao.KakaoClient
import kr.disdong.knitting.auth.kakao.KakaoService
import kr.disdong.knitting.common.token.TokenManager
import kr.disdong.knitting.mysql.repository.UserRepository
import org.junit.jupiter.api.Nested

internal class AuthKakaoServiceTest {

    private var kakaoClient: KakaoClient = mockk()

    private var userRepository: UserRepository = mockk()

    private var tokenManager: TokenManager = TokenManager("123", ObjectMapper())

    val sut = KakaoService(kakaoClient, userRepository, tokenManager)

    @Nested
    inner class T1 {

        // @Test
        // fun t1() {
        //     val request = OAuthCallbackResponse(code = "hello")
        //
        //     every { kakaoService.getToken(request) } returns TokenResponse()
        //     when(kakaoService.getToken(request))
        //
        //
        //     sut.login(request)
        //
        // }
    }
}
