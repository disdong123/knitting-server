package kr.disdong.knitting.server.core.filter

import kr.disdong.knitting.common.dto.KnittingResponse
import kr.disdong.knitting.common.exception.KnittingException
import kr.disdong.knitting.common.logger.logger
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 *
 */
@RestControllerAdvice
class ExceptionAdvice {

    private val logger = logger<ExceptionAdvice>()

    /**
     *
     */
    @ExceptionHandler(KnittingException::class)
    @ResponseBody
    fun knittingException(e: KnittingException): KnittingResponse<KnittingException> {
        val result = KnittingResponse.of<KnittingException>(e)
        logger.error("knittingException: $e")
        return result
    }

    /**
     *
     */
    @ExceptionHandler(RuntimeException::class)
    @ResponseBody
    fun runtimeException(e: RuntimeException): ResponseEntity<Map<String, String>> {
        println(e)
        println("${e.message} ${e.stackTrace}")

        val responseHeaders = HttpHeaders()

        val httpStatus = HttpStatus.BAD_REQUEST

        val map = mutableMapOf<String, String>()

        map["error type"] = httpStatus.reasonPhrase
        map["code"] = "400"
        map["message"] = "에러발생"

        return ResponseEntity(map, responseHeaders, httpStatus)
    }
}
