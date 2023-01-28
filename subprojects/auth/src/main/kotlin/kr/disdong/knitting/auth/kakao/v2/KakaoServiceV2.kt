package kr.disdong.knitting.auth.kakao.v2

import kr.disdong.knitting.auth.common.exception.AuthorizationCodeAccessDeniedException
import kr.disdong.knitting.auth.common.exception.GetTokenFailedException
import kr.disdong.knitting.auth.kakao.v2.dto.LogoutResponseV2
import kr.disdong.knitting.auth.kakao.v2.dto.OAuthCallbackResponseV2
import kr.disdong.knitting.auth.kakao.v2.dto.TokenResponseCamelV2
import kr.disdong.knitting.common.logger.logger
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class KakaoServiceV2(
    private val kakaoClientV2: KakaoClientV2
) {
    private val logger = logger<KakaoServiceV2>()

    /**
     * access token 을 가져옵니다.
     * @param response
     */
    @Throws(GetTokenFailedException::class)
    fun getToken(response: OAuthCallbackResponseV2): TokenResponseCamelV2 {
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
    fun logout(accessToken: String): ResponseEntity<LogoutResponseV2> {
        logger.info("logout: $accessToken")

        return kakaoClientV2.logout(accessToken)
    }
}
