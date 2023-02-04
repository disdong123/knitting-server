package kr.disdong.knitting.common.dto

import kr.disdong.knitting.common.exception.HandlerExceptionType
import org.springframework.http.HttpStatus

class KnittingResponse<T>(
    val status: String,
    val code: Int,
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        fun <T> of(
            exception: Exception
        ): KnittingResponse<T> {
            val standardThrowableType = HandlerExceptionType.of(exception)
            return KnittingResponse(
                status = standardThrowableType.status,
                code = standardThrowableType.code.value(),
                message = exception.message ?: standardThrowableType.message,
            )
        }

        fun <T> of(
            content: T? = null
        ): KnittingResponse<T> {
            return KnittingResponse(
                status = "ok",
                code = HttpStatus.OK.value(),
                data = content
            )
        }
    }
}
