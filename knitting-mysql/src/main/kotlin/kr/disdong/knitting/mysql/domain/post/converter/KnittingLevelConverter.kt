package kr.disdong.knitting.mysql.domain.post.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

enum class KnittingLevel(val value: String) {
    BEGINNER("초급"),
    INTERMEDIATE("중급"),
    ADVANCED("고급");

    companion object {
        fun fromValue(value: String): KnittingLevel {
            return values().associateBy { it.value }[value]!!
        }
    }
}

@Converter
class KnittingLevelConverter : AttributeConverter<KnittingLevel, String> {
    override fun convertToDatabaseColumn(attribute: KnittingLevel): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(s: String): KnittingLevel {
        return KnittingLevel.fromValue(s)
    }
}
