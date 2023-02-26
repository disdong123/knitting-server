package kr.disdong.knitting.mysql.domain.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.disdong.knitting.common.token.Token

@Converter
class TokenConverter : AttributeConverter<Token, String> {
    override fun convertToDatabaseColumn(attribute: Token?): String? {
        if (attribute == null) {
            return null
        }

        return attribute.value
    }

    override fun convertToEntityAttribute(s: String?): Token? {
        if (s == null) {
            return null
        }

        return Token(value = s)
    }
}
