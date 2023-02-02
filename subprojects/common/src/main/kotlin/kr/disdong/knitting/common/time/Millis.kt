package kr.disdong.knitting.common.time

/**
 * TODO
 *
 * @property time
 */
enum class Millis(
    val time: Int
) {
    HOUR(1000 * 60 * 60 * 1),
    TWO_WEEKS(1000 * 60 * 60 * 24 * 14);
}
