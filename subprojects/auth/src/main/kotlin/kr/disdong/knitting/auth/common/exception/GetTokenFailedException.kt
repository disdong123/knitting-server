package kr.disdong.knitting.auth.common.exception

import kr.disdong.knitting.auth.kakao.v1.dto.TokenResponse

/**
 *
 * @constructor
 * TODO
 *
 * @param getTokenResponse
 */
class GetTokenFailedException(
    private val getTokenResponse: TokenResponse?,
) : AuthException("Resource server 로 부터 토큰을 받지 못했습니다.") {

    override fun getCode(): Int {
        return 400
    }
}
