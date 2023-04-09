package kr.disdong.knitting.mysql.domain.post.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

enum class PriceType(val value: String) {
    FREE("무료"),
    PAID("유료");

    companion object {
        fun fromValue(value: String): PriceType {
            return values().associateBy { it.value }[value]!!
        }
    }
}

@Converter
class PriceTypeConverter : AttributeConverter<PriceType, String> {
    override fun convertToDatabaseColumn(attribute: PriceType): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(s: String): PriceType {
        return PriceType.fromValue(s)
    }
}
