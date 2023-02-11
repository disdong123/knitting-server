package kr.disdong.knitting.common.token

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import kr.disdong.knitting.common.time.Millis
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TokenManagerTest {
    data class User(val test: String)

    private val key = "abcde12345"
    @Test
    fun `custom claims 를 넣어서 jwt 를 생성하고, 생성된 토큰에서 claims 를 받아온다`() {
        val tokenManager = TokenManager(key, ObjectMapper())

        val token = tokenManager.create("user", User("hankil"), Millis.HOUR)

        val claims = tokenManager.getCustomClaims(token, object : TypeReference<User>() {})

        assertEquals(claims.test, "hankil")
    }
}
