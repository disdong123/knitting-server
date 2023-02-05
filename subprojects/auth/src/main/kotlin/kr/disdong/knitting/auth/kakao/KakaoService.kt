package kr.disdong.knitting.auth.kakao

import jakarta.servlet.http.HttpServletResponse
import kr.disdong.knitting.auth.common.exception.AuthorizationCodeAccessDeniedException
import kr.disdong.knitting.auth.common.exception.GetTokenFailedException
import kr.disdong.knitting.auth.kakao.dto.LogoutResponse
import kr.disdong.knitting.auth.kakao.dto.OAuthCallbackResponse
import kr.disdong.knitting.auth.kakao.dto.TokenResponseCamel
import kr.disdong.knitting.common.logger.logger
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class KakaoService(
    private val kakaoClientV2: KakaoClient
) {
    private val logger = logger<KakaoService>()

    /**
     *
     * @param httpServletResponse
     */
    fun login(httpServletResponse: HttpServletResponse) {

        kakaoClientV2.login(httpServletResponse)
    }

    /**
     * access token 을 가져옵니다.
     * @param response
     */
    @Throws(GetTokenFailedException::class)
    fun getToken(response: OAuthCallbackResponse): TokenResponseCamel {
        logger.info("getToken: $response")

        if (response.error == "access_denied") {
            logger.error("response.error_description: ${response.error_description}")
            throw AuthorizationCodeAccessDeniedException()
        }

        val tokenResponse = kakaoClientV2.getToken(response)

        logger.info("tokenResponse: $tokenResponse")

        return tokenResponse
    }

    /**
     *
     * @param accessToken
     * @return
     */
    fun logout(accessToken: String): ResponseEntity<LogoutResponse> {
        logger.info("logout: $accessToken")

        return kakaoClientV2.logout(accessToken)
    }
}
