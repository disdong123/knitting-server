package kr.disdong.knitting.auth.common.exception

import kr.disdong.knitting.common.exception.KnittingException

/**
 *
 * @property message
 */
abstract class AuthException(
    override val message: String,
) : KnittingException(message) {

    abstract override fun getCode(): Int
}
