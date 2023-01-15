package kr.disdong.knitting.auth.core.filter

import kr.disdong.knitting.auth.common.exception.AuthException
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

    /**
     *
     */
    @ExceptionHandler(AuthException::class)
    @ResponseBody
    fun customException(e: AuthException): ResponseEntity<Map<String, String>> {
        val responseHeaders = HttpHeaders()

        val map = mutableMapOf<String, String>()

        map["code"] = e.getCode().toString()
        map["message"] = e.message

        return ResponseEntity(map, responseHeaders, e.getCode())
    }

    /**
     *
     */
    @ExceptionHandler(RuntimeException::class)
    @ResponseBody
    fun runtimeException(e: RuntimeException): ResponseEntity<Map<String, String>> {
        println(e)

        val responseHeaders = HttpHeaders()

        val httpStatus = HttpStatus.BAD_REQUEST

        val map = mutableMapOf<String, String>()

        map["error type"] = httpStatus.reasonPhrase
        map["code"] = "400"
        map["message"] = "에러발생"

        return ResponseEntity(map, responseHeaders, httpStatus)
    }
}
