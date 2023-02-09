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
    fun a() {
        val tokenManager = TokenManager(key, ObjectMapper())

        val token = tokenManager.create("user", User("hankil"), Millis.HOUR)

        val claims = tokenManager.getCustomClaims(token, object : TypeReference<User>() {})

        assertEquals(claims.test, "hankil")
    }
}
