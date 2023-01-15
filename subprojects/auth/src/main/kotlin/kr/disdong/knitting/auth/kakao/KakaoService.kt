package kr.disdong.knitting.auth.kakao

import jakarta.servlet.http.HttpServletResponse
import kr.disdong.knitting.auth.common.exception.GetTokenFailedException
import kr.disdong.knitting.auth.kakao.dto.GetTokenResponseCamel
import kr.disdong.knitting.auth.kakao.dto.LogoutResponse
import kr.disdong.knitting.auth.kakao.dto.OAuthCallbackResponse
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
    fun getToken(response: OAuthCallbackResponse): GetTokenResponseCamel {
        logger.info("getToken: $response")

        if (response.error == "access_denied") {
            logger.info("response.error_description: ${response.error_description}")
        }

        val getTokenResponse = kakaoClient.getToken(response)

        logger.info("getTokenResponse: $getTokenResponse")

        return getTokenResponse
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
