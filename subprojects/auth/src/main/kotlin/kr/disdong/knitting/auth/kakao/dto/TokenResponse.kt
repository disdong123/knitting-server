package kr.disdong.knitting.auth.kakao.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import kr.disdong.knitting.common.token.Token
import kr.disdong.knitting.common.token.TokenDeserializer
import java.util.Base64

/**
 * id token, access token, refresh token 모두 포함됩니다.
 */
data class TokenResponse(
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("access_token")
    @JsonDeserialize(using = TokenDeserializer::class)
    val accessToken: Token,
    @JsonProperty("id_token")
    @JsonDeserialize(using = TokenDeserializer::class)
    val idToken: Token? = null,
    @JsonProperty("expires_in")
    val expiresIn: Int,
    @JsonProperty("refresh_token")
    @JsonDeserialize(using = TokenDeserializer::class)
    val refreshToken: Token,
    @JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: Int,
    @JsonProperty("scope")
    val scope: String? = null, // openId 인경우 openid 포함
) {

    /**
     *
     * @return
     */
    fun toArrayIdToken(): List<String> {
        return idToken!!.value.split(".")
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

// data class TokenResponse(
//     val token_type: String,
//     val access_token: String,
//     val id_token: String? = null,
//     val expires_in: Int,
//     val refresh_token: String,
//     val refresh_token_expires_in: Int,
//     val scope: String? = null, // openId 인경우 openid 포함
// ) {
//
//     /**
//      *
//      * @return
//      */
//     fun toCamel(): TokenResponseCamel {
//         return TokenResponseCamel(
//             token_type,
//             access_token,
//             id_token,
//             expires_in,
//             refresh_token,
//             refresh_token_expires_in,
//             scope
//         )
//     }
// }
//
// data class TokenResponseCamel(
//     val tokenType: String,
//     val accessToken: String,
//     val idToken: String? = null,
//     val expiresIn: Int,
//     val refreshToken: String,
//     val refreshToken_expires_in: Int,
//     val scope: String? = null,
// ) {
//
//     /**
//      *
//      * @return
//      */
//     fun toArrayIdToken(): List<String> {
//         return idToken!!.split(".")
//     }
//
//     /**
//      *
//      * @return
//      */
//     fun decodeIdToken(): IdToken {
//         val decoder = Base64.getUrlDecoder()
//
//         return IdToken.of(String(decoder.decode(toArrayIdToken()[1])))
//     }
// }
