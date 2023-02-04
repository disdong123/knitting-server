package kr.disdong.knitting.auth.kakao.v1.dto

import java.util.Base64

/**
 * id token, access token, refresh token 모두 포함됩니다.
 */
data class TokenResponse(
    val token_type: String,
    val access_token: String,
    val id_token: String? = null,
    val expires_in: Int,
    val refresh_token: String,
    val refresh_token_expires_in: Int,
    val scope: String? = null, // openId 인경우 openid 포함
) {

    /**
     *
     * @return
     */
    fun toCamel(): TokenResponseCamel {
        return TokenResponseCamel(
            token_type,
            access_token,
            id_token,
            expires_in,
            refresh_token,
            refresh_token_expires_in,
            scope
        )
    }
}

data class TokenResponseCamel(
    val tokenType: String,
    val accessToken: String,
    val idToken: String? = null,
    val expiresIn: Int,
    val refreshToken: String,
    val refreshToken_expires_in: Int,
    val scope: String? = null,
) {

    /**
     *
     * @return
     */
    fun toArrayIdToken(): List<String> {
        return idToken!!.split(".")
    }

    /**
     *
     * @return
     */
    fun decodeIdToken(): IdToken {
        val decoder = Base64.getUrlDecoder()

        return IdToken.of(String(decoder.decode(toArrayIdToken()[1])))
    }
}