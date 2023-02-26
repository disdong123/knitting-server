package kr.disdong.knitting.server.core.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.disdong.knitting.auth.common.exception.InvalidAccessTokenException
import kr.disdong.knitting.auth.kakao.KakaoService
import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.common.logger.logger
import kr.disdong.knitting.common.token.Token
import kr.disdong.knitting.mysql.repository.UserRepository
import kr.disdong.knitting.server.common.annotation.AuthGuard
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
    private lateinit var authKakaoService: KakaoService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        try {
            val auth = (handler as HandlerMethod).getMethodAnnotation(AuthGuard::class.java)
            logger.info("preHandle $auth")
            auth ?: return true
        } catch (err: RuntimeException) {
            logger.debug("AuthGuard 어노테이션이 없는 경우에는 true 를 반환합니다.")
            return true
        }

        var authorization = request.getHeader("Authorization")

        // TODO 테스트용입니다.
        // if (authorization == null) {
        //     authorization = "Bearer 01038249970"
        // }

        val token = validateAuthorizationHeader(authorization)

        if (token.isPhone()) {
            val user = userRepository.findByPhone(token.value) ?: throw InvalidAccessTokenException(token)

            setCustomClaimsOnRequest(request, AccessTokenClaims(user.id!!))

            return true
        }

        val claims = authKakaoService.verifyAccessToken(token)

        logger.info("claims: $claims")

        setCustomClaimsOnRequest(request, claims)

        return true
    }

    /**
     * TODO.
     *  setAttribute 로 custom claims 값을 저장합니다.
     *  Request 차이 확인. scope 는 뭐지??
     * @param request
     * @param claims
     */
    private fun setCustomClaimsOnRequest(request: HttpServletRequest, claims: AccessTokenClaims) {
        ServletWebRequest(request).setAttribute("AccessTokenClaims", claims, 0)
    }

    /**
     * 정상적인 헤더값인지 확인한 후, 토큰을 반환합니다.
     * @param authorization
     * @return
     */
    private fun validateAuthorizationHeader(authorization: String?): Token {
        if (authorization == null) {
            logger.debug("토큰이 제공되지 않았습니다.")
            throw InvalidAccessTokenException()
        }

        val tokens = authorization.split(" ")

        if (tokens.size < 2) {
            logger.debug("토큰이 제공되지 않았습니다.")
            throw InvalidAccessTokenException()
        }

        if (tokens[0] != "Bearer" || tokens[1] == "") {
            logger.info("token 이 없습니다.")
            throw InvalidAccessTokenException()
        }

        return Token(tokens[1])
    }
}
