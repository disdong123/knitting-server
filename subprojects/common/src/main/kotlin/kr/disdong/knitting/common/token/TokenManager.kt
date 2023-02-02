package kr.disdong.knitting.common.token

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.*
import kr.disdong.knitting.common.time.Millis
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

@Component
class TokenManager<T>(
    @Value("\${jwt.secret-key}")
    private val secretKey: String,
    private val objectMapper: ObjectMapper,
) {
    val ISSUER = "babypig.click"

    /**
     * 토큰 제목과 커스텀 클레임을 받아서 토큰을 반환합니다.
     * @param subject
     * @param payload
     * @return
     */
    fun create(subject: String, payload: T, expiredTime: Millis): Token {
        return Token(
            Jwts.builder()
                .setHeader(setHeader())
                .setClaims(setCustomClaims(payload))
                .setSubject("subject") // 토큰 제목
                .setIssuer(ISSUER)
                .setExpiration(setExpiration(expiredTime.time))
                .signWith(SignatureAlgorithm.HS256, setSigningKey()) // HS256과 Key 로 Sign
                .compact()
        )
    }

    /**
     * 토큰타입과 알고리즘을 설정합니다.
     * @return
     */
    private fun setHeader(): Map<String, Any> {
        return objectMapper
            .convertValue(JwtHeader(), Map::class.java) as Map<String, Any>
    }

    /**
     * key, value 로 이루어진 claim 들을 설정합니다.
     * @return
     */
    private fun setCustomClaims(payload: T): MutableMap<String, Any> {
        return objectMapper
            .convertValue(payload, Map::class.java) as MutableMap<String, Any>
    }

    /**
     * 토큰 만료시간을 설정합니다.
     * @return
     */
    private fun setExpiration(expiredTime: Int): Date {
        val ext = Date()

        ext.setTime(ext.getTime() + expiredTime)

        return ext
    }

    /**
     * 서명된 키를 반환합니다.
     * @return
     */
    private fun setSigningKey(): SecretKeySpec {
        return SecretKeySpec(DatatypeConverter.parseBase64Binary(secretKey), SignatureAlgorithm.HS256.jcaName)
    }
}
