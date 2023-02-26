package kr.disdong.knitting.common.dto

import kr.disdong.knitting.common.exception.KnittingException
import org.springframework.http.HttpStatus

class KnittingResponse<T>(
    val code: Int,
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        fun <T> of(
            exception: KnittingException
        ): KnittingResponse<T> {
            return KnittingResponse(
                code = exception.getCode(),
                message = exception.message,
            )
        }

        fun <T> of(
            content: T? = null,
        ): KnittingResponse<T> {
            return KnittingResponse(
                code = HttpStatus.OK.value(),
                data = content
            )
        }

        fun <T> of(
            code: HttpStatus,
            content: T? = null,
        ): KnittingResponse<T> {
            return KnittingResponse(
                code = code.value(),
                data = content
            )
        }
    }
}
