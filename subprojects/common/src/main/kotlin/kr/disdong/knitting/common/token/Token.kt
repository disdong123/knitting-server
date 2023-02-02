package kr.disdong.knitting.common.token

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.jsonwebtoken.Jwts
import javax.xml.bind.DatatypeConverter

data class Token(
    val value: String,
) {

    /**
     * subject 를 가져옵니다.
     * @return
     */
    fun getSubject(secretKey: String): String {
        return Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
            .parseClaimsJws(value).body
            .subject
    }

    /**
     * 커스텀 클레임을 가져옵니다.
     * sub=subject, iss=babypig.click, exp=1675093709 값은 default 로 존재하므로 제외합니다.
     * @return
     */
    fun <T> getCustomClaims(secretKey: String, type: TypeReference<T>): T {
        val claims = Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
            .parseClaimsJws(value).body

        claims.remove("sub")
        claims.remove("iss")
        claims.remove("exp")

        return jacksonObjectMapper().convertValue(claims, type)
    }
}
