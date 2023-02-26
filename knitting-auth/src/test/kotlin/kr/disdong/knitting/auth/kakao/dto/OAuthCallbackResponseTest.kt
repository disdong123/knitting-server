package kr.disdong.knitting.auth.kakao.dto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class OAuthCallbackResponseTest {

    @Nested
    @DisplayName("isAccessDenied 메서드는")
    inner class T1 {
        @Test
        fun `error 가 access_denied 인 경우 true 를 반환한다`() {
            val response = OAuthCallbackResponse(code = null, error = "access_denied")

            assertEquals(response.isAccessDenied(), true)
        }

        @Test
        fun `error 가 access_denied 가 아닌 경우 false 를 반환한다`() {
            val response = OAuthCallbackResponse(code = null, error = "not_found")

            assertEquals(response.isAccessDenied(), false)
        }
    }
}
