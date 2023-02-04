package kr.disdong.knitting.common.exception
import org.springframework.http.HttpStatus

enum class HandlerExceptionType(
    val status: String,
    val code: HttpStatus,
    val message: String,
    val exceptions: Set<Any>
) {
    // 400
    BAD_REQUEST("error", HttpStatus.BAD_REQUEST, "bad request", setOf()),

    // 404
    NOT_FOUND(
        "error", HttpStatus.NOT_FOUND, "not found exception",
        setOf()
    ),

    // 500
    UNKNOWN("error", HttpStatus.INTERNAL_SERVER_ERROR, "internal server error", setOf());

    companion object {
        fun of(throwable: Throwable): HandlerExceptionType {
            return values().find { it.exceptions.contains(throwable::class) } ?: UNKNOWN
        }
    }
}
