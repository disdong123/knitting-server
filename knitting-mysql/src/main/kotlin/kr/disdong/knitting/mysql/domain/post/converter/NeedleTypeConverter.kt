package kr.disdong.knitting.mysql.domain.post.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

enum class NeedleType(val value: String) {
    CROCHET("코바늘"),
    KNITTING("대바늘");

    companion object {
        fun fromValue(value: String): NeedleType {
            return values().associateBy { it.value }[value]!!
        }
    }
}

@Converter
class NeedleTypeConverter : AttributeConverter<NeedleType, String> {
    override fun convertToDatabaseColumn(attribute: NeedleType): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(s: String): NeedleType {
        return NeedleType.fromValue(s)
    }
}
