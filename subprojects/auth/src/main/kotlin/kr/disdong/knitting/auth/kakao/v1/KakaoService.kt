package kr.disdong.knitting.auth.kakao.v1

import jakarta.servlet.http.HttpServletResponse
import kr.disdong.knitting.auth.common.exception.AuthorizationCodeAccessDeniedException
import kr.disdong.knitting.auth.common.exception.GetTokenFailedException
import kr.disdong.knitting.auth.kakao.v1.dto.LogoutResponse
import kr.disdong.knitting.auth.kakao.v1.dto.OAuthCallbackResponse
import kr.disdong.knitting.auth.kakao.v1.dto.TokenResponseCamel
import kr.disdong.knitting.common.logger.logger
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class KakaoService(
    private val kakaoClient: KakaoClient
) {
    private val logger = logger<KakaoService>()

    /**
     *
     * @param httpServletResponse
     */
    fun login(httpServletResponse: HttpServletResponse) {

        kakaoClient.login(httpServletResponse)
    }

    /**
     *
     * @param response
     */
    @Throws(GetTokenFailedException::class)
    fun getToken(response: OAuthCallbackResponse): TokenResponseCamel {
        logger.info("getToken: $response")

        if (response.error == "access_denied") {
            logger.error("response.error_description: ${response.error_description}")
            throw AuthorizationCodeAccessDeniedException()
        }

        val tokenResponse = kakaoClient.getToken(response)

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

        return kakaoClient.logout(accessToken)
    }
}
