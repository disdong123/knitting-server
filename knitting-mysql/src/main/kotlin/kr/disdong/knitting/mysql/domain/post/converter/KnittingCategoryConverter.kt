package kr.disdong.knitting.mysql.domain.post.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

enum class KnittingCategory(val value: String) {
    CLOTHING("의류"),
    ACCESSORY("악세사리"),
    HOME("홈"),
    PET("애완"),
    TOY("장난감"),
    SWEATER("스웨터"),
    CARDIGAN("카디건"),
    HAT("모자"),
    SCARF("스카프"),
    GLOVES("장갑"),
    SOCKS("양말"),
    SHOES("신발"),
    BAG("가방"),
    BLANKET("담요"),
    OTHER("기타");

    companion object {
        fun fromValue(value: String): KnittingCategory {
            return values().associateBy { it.value }[value]!!
        }
    }
}

@Converter
class KnittingCategoryConverter : AttributeConverter<KnittingCategory, String> {
    override fun convertToDatabaseColumn(attribute: KnittingCategory): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(s: String): KnittingCategory {
        return KnittingCategory.fromValue(s)
    }
}
