package kr.disdong.knitting.common.exception

import java.lang.RuntimeException

/**
 *
 * @property message
 */
abstract class KnittingException(
    override val message: String,
) : RuntimeException(message) {

    abstract fun getCode(): Int
}
