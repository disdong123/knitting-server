// package kr.disdong.knitting.server.module.auth.kakao.service
//
// import com.fasterxml.jackson.databind.ObjectMapper
// import io.mockk.mockk
// import kr.disdong.knitting.auth.kakao.KakaoService
// import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
// import kr.disdong.knitting.auth.kakao.dto.OAuthCallbackResponse
// import kr.disdong.knitting.common.token.TokenManager
// import kr.disdong.knitting.domain.jpa.repository.UserRepository
// import org.junit.jupiter.api.Assertions.*
// import org.junit.jupiter.api.Nested
// import org.junit.jupiter.api.Test
// import org.mockito.Mock
// import org.mockito.Mockito.mock
// import org.springframework.beans.factory.annotation.Autowired
// import org.springframework.boot.test.mock.mockito.MockBean
//
// internal class AuthKakaoServiceTest {
//
//     private var kakaoService: KakaoService = mockk()
//
//     private var userRepository: UserRepository = mockk()
//
//     private var tokenManager: TokenManager<AccessTokenClaims> = TokenManager("123", ObjectMapper())
//
//     val sut = AuthKakaoService(kakaoService, userRepository, tokenManager)
//
//     @Nested
//     inner class T1 {
//
//         @Test
//         fun t1() {
//             println(sut)
//             OAuthCallbackResponse()
//
//             sut.login()
//
//         }
//     }
// }
