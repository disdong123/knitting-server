package kr.disdong.knitting.auth.kakao

import jakarta.servlet.http.HttpServletResponse
import kr.disdong.knitting.auth.common.exception.GetTokenFailedException
import kr.disdong.knitting.auth.kakao.dto.LogoutResponse
import kr.disdong.knitting.auth.kakao.dto.OAuthCallbackResponse
import kr.disdong.knitting.auth.kakao.dto.TokenResponse
import kr.disdong.knitting.auth.kakao.dto.TokenResponseCamel
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Component
class KakaoClient(
    // v2 는 client 에서 카카오 페이지를 띄웁니다. 따라서 클라이언트에게 받습니다.
    @Value("\${kakao.oauth.v1.redirect.uri}")
    private val REDIRECT_URI: String,
    @Value("\${kakao.oauth.v1.client.id}")
    private val CLIENT_ID: String,
) {

    private val url = "https://kauth.kakao.com/oauth"

    private val restTemplate = RestTemplate()

    /**
     * kakao 로그인 페이지로 리다이렉트시킵니다.
     * @param httpServletResponse
     */
    fun login(httpServletResponse: HttpServletResponse) {
        httpServletResponse.sendRedirect("$url/authorize?client_id=$CLIENT_ID&redirect_uri=$REDIRECT_URI&response_type=code&scope=openid")
    }

    /**
     * code 를 이용하여 access token 을 가져옵니다.
     * @param response
     */
    @Throws(GetTokenFailedException::class)
    fun getToken(response: OAuthCallbackResponse): TokenResponseCamel {
        val header = HttpHeaders()

        header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        // TODO dto 를 사용할 수 있는지 확인 필요.
        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        body.add("grant_type", "authorization_code")
        body.add("client_id", CLIENT_ID)
        body.add("redirect_uri", response.redirectUri) // client 에서 받아옵니다.
        body.add("code", response.code)

        val request = HttpEntity(body, header)

        return restTemplate
            .exchange("$url/token", HttpMethod.POST, request, TokenResponse::class.java)
            .body
            ?.toCamel() ?: throw GetTokenFailedException(null)
    }

    /**
     * 로그아웃합니다.
     * 사용자 액세스 토큰과 리프레시 토큰을 모두 만료시킵니다.
     * @param accessToken
     */
    fun logout(accessToken: String): ResponseEntity<LogoutResponse> {
        val header = HttpHeaders()

        header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        header.add("Authorization", "Bearer $accessToken")

        val request = HttpEntity<Object>(header)

        return restTemplate.exchange("https://kapi.kakao.com/v1/user/logout", HttpMethod.POST, request, LogoutResponse::class.java)
    }
}
