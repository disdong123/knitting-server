package kr.disdong.knitting.auth.kakao

import jakarta.servlet.http.HttpServletResponse
import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.auth.kakao.dto.LogoutResponse
import kr.disdong.knitting.auth.kakao.dto.OAuthCallbackResponse
import kr.disdong.knitting.auth.kakao.dto.RefreshAccessTokenResponse
import kr.disdong.knitting.auth.kakao.dto.TokenResponse
import kr.disdong.knitting.common.token.Token
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
    @Value("\${kakao.oauth.v1.redirect.uri}")
    private val REDIRECT_URI: String,
    @Value("\${kakao.oauth.v1.logout.redirect.uri}")
    private val LOGOUT_REDIRECT_URI: String,
    @Value("\${kakao.oauth.v1.client.id}")
    private val CLIENT_ID: String,
) {

    // TODO.
    //  https://developers.kakao.com/docs/latest/ko/kakaologin/trouble-shooting
    //  예외처리 필요.
    private val kauthUrl = "https://kauth.kakao.com/oauth"
    private val kapiUrl = "https://kapi.kakao.com/v1"

    private val restTemplate = RestTemplate()

    /**
     * kakao 로그인 페이지로 리다이렉트시킵니다.
     * @param httpServletResponse
     */
    fun login(httpServletResponse: HttpServletResponse) {
        httpServletResponse.sendRedirect("$kauthUrl/authorize?client_id=$CLIENT_ID&redirect_uri=$REDIRECT_URI&response_type=code&scope=openid")
    }

    /**
     * code 를 이용하여 access token 을 가져옵니다.
     * @param response
     */
    fun getTokenWithAuthCode(response: OAuthCallbackResponse): TokenResponse {
        val request = makeGetTokenRequest(response)

        return restTemplate
            .exchange("$kauthUrl/token", HttpMethod.POST, request, TokenResponse::class.java)
            .body!!
    }

    /**
     * 로그아웃합니다.
     * 사용자 액세스 토큰과 리프레시 토큰을 모두 만료시킵니다.
     * @param accessToken
     */
    fun logout(accessToken: Token): ResponseEntity<LogoutResponse> {
        val request = makeLogoutRequest(accessToken)
        return restTemplate.exchange("https://kapi.kakao.com/v1/user/logout", HttpMethod.POST, request, LogoutResponse::class.java)
    }

    /**
     *
     * @param httpServletResponse
     */
    fun logoutWithKakao(httpServletResponse: HttpServletResponse, claims: AccessTokenClaims) {
        httpServletResponse.sendRedirect("$kauthUrl/logout?client_id=$CLIENT_ID&logout_redirect_uri=$LOGOUT_REDIRECT_URI&state=${claims.id}")
    }

    /**
     *
     * @param refreshToken
     */
    fun refreshAccessToken(refreshToken: Token): RefreshAccessTokenResponse {
        val request = makeRefreshAccessTokenRequest(refreshToken)

        return restTemplate
            .exchange("$kauthUrl/token", HttpMethod.POST, request, RefreshAccessTokenResponse::class.java)
            .body!!
    }

    private fun makeGetTokenRequest(response: OAuthCallbackResponse): HttpEntity<MultiValueMap<String, String>> {
        val header = HttpHeaders()

        header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        // TODO dto 를 사용할 수 있는지 확인 필요.
        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        body.add("grant_type", "authorization_code")
        body.add("client_id", CLIENT_ID)
        body.add("redirect_uri", response.redirectUri ?: REDIRECT_URI) // client 에서 받아옵니다.
        body.add("code", response.code?.value)

        return HttpEntity(body, header)
    }

    private fun makeLogoutRequest(accessToken: Token): HttpEntity<Object> {
        val header = HttpHeaders()

        header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        header.add("Authorization", "Bearer ${accessToken.value}")

        return HttpEntity<Object>(header)
    }

    private fun makeRefreshAccessTokenRequest(refreshToken: Token): HttpEntity<MultiValueMap<String, String>> {
        val header = HttpHeaders()

        header.add("Content-type", "application/x-www-form-urlencoded")

        // TODO dto 를 사용할 수 있는지 확인 필요.
        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        body.add("grant_type", "refresh_token")
        body.add("client_id", CLIENT_ID)
        body.add("refresh_token", refreshToken.value) // client 에서 받아옵니다.

        return HttpEntity(body, header)
    }
}

// /**
//  * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#refresh-token-response
//  * @property token_type
//  * @property access_token
//  * @property id_token
//  * @property expires_in
//  * @property refresh_token
//  * @property refresh_token_expires_in
//  */
// class RefreshAccessTokenResponse(
//     val token_type: String, //
//     val access_token: String,
//     val id_token: String?,
//     val expires_in: Int,
//     val refresh_token: String?,
//     val refresh_token_expires_in: Int?,
// ) {
//
//     fun toCamel(): RefreshAccessTokenResponseCamel {
//         return RefreshAccessTokenResponseCamel(
//             token_type,
//             Token(access_token),
//             id_token,
//             expires_in,
//             (if(refresh_token != null) Token(refresh_token)
//             else refresh_token),
//             refresh_token_expires_in
//         )
//     }
// }
//
// class RefreshAccessTokenResponseCamel(
//     val tokenType: String,
//     val accessToken: Token,
//     val idToken: String?,
//     val expiresIn: Int,
//     val refreshToken: Token?,
//     val refreshTokenExpiresIn: Int?,
// )
