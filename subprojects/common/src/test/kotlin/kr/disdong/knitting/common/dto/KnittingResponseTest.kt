package kr.disdong.knitting.common.dto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

internal class KnittingResponseTest {

    @Nested
    @DisplayName("비지니스가 성공적으로 끝낫을 경우")
    inner class T1 {
        @Test
        fun `기본적으로 200 code 를 반환합니다`() {
            val response = KnittingResponse.of(content = "hello")

            assertEquals(response.data, "hello")
            assertEquals(response.code, 200)
        }

        @Test
        fun `인자로 받은 code 값을 반환합니다`() {
            val response = KnittingResponse.of(content = "hello", code = HttpStatus.CREATED)

            assertEquals(response.data, "hello")
            assertEquals(response.code, 201)
        }
    }
}
