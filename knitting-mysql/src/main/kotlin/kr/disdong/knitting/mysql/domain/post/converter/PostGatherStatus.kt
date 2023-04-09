package kr.disdong.knitting.mysql.domain.post.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

enum class PostGatherStatus(val value: String) {
    ALL("전체"),
    GATHERING("모집중"),
    COMPLETED("모집완료"),
    DONE("뜨개완료");

    companion object {
        fun fromValue(value: String): PostGatherStatus {
            return values().associateBy { it.value }[value]!!
        }
    }
}

@Converter
class PostGatherStatusConverter : AttributeConverter<PostGatherStatus, String> {
    override fun convertToDatabaseColumn(attribute: PostGatherStatus): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(s: String): PostGatherStatus {
        return PostGatherStatus.fromValue(s)
    }
}
