package kr.disdong.knitting.common.token

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import kr.disdong.knitting.common.time.Millis
import org.junit.jupiter.api.Test

internal class TokenManagerTest {
    data class User(val test: String)
    @Test
    fun a() {
        val secretKey = "abcde12345"
        val tokenManager = TokenManager<User>(secretKey, ObjectMapper())

        val token = tokenManager.create("user", User("hankil"), Millis.HOUR)

        println(token)
        val a = tokenManager.getCustomClaims(token, object : TypeReference<User>() {})

        println(a.test)
    }
}
