package kr.disdong.knitting.common.token

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer

class TokenSerializer : StdSerializer<Token>(Token::class.java) {
    override fun serialize(value: Token?, gen: JsonGenerator, provider: SerializerProvider?) {
        if (value == null) {
            gen.writeNull()
        }

        gen.writeString(value?.value)
    }
}
