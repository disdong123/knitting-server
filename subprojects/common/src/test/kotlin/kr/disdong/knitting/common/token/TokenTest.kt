package kr.disdong.knitting.common.token

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TokenTest {

    @Test
    fun t1() {
        val token = Token("01023423414")
        assertEquals(token.isPhone(), true)
    }

    @Test
    fun t2() {
        val token = Token("01#!@#!@#!@#!23")
        assertEquals(token.isPhone(), false)
    }
}
