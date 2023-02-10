package kr.disdong.knitting.common.token

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer

class TokenDeserializer : StdDeserializer<Token?>(Token::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Token? {
        println("??? ${p.text}")
        if (p.text == null) {
            return null
        }

        return Token(p.text)
    }
}
