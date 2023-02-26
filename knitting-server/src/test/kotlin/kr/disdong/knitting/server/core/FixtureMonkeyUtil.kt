package kr.disdong.knitting.server.core

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin

class FixtureMonkeyUtil {
    companion object {
        fun monkey(): FixtureMonkey = FixtureMonkey.labMonkeyBuilder()
            .plugin(KotlinPlugin()).build()
    }
}
