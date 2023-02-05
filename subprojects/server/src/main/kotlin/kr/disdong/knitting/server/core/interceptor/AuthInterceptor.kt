package kr.disdong.knitting.server.core.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.disdong.knitting.auth.common.exception.InvalidAccessTokenException
import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.common.logger.logger
import kr.disdong.knitting.common.token.Token
import kr.disdong.knitting.domain.jpa.repository.UserRepository
import kr.disdong.knitting.server.common.annotation.AuthGuard
import kr.disdong.knitting.server.module.auth.kakao.service.AuthKakaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import java.lang.RuntimeException

@Component
class AuthInterceptor : HandlerInterceptor {

    private val logger = logger<AuthInterceptor>()

    @Autowired
    private lateinit var authKakaoService: AuthKakaoService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        try {
            val auth = (handler as HandlerMethod).getMethodAnnotation(AuthGuard::class.java)
            logger.info("preHandle $auth")
            auth ?: return true
        } catch (err: RuntimeException) {
            return true
        }

        val authorization = request.getHeader("Authorization")

        logger.info("authorization: $authorization")

        if (authorization == null) {
            throw InvalidAccessTokenException()
        }

        val tokens = authorization.split(" ")

        if (tokens.size < 2) {
            throw InvalidAccessTokenException()
        }

        if (tokens[0] != "Bearer" || tokens[1] == "") {
            logger.info("token 이 없습니다.")
            throw InvalidAccessTokenException()
        }

        val token = tokens[1]

        if (token.startsWith("010")) {
            val user = userRepository.findByPhone(token) ?: throw InvalidAccessTokenException(Token(token))

            ServletWebRequest(request).setAttribute("AccessTokenClaims", AccessTokenClaims(user.id!!), 0)

            return true
        }

        val claims = authKakaoService.verifyAccessToken(Token(token))

        logger.info("claims: $claims")

        // TODO. Request 차이. scope??
        ServletWebRequest(request).setAttribute("AccessTokenClaims", claims, 0)

        return true
    }
}
