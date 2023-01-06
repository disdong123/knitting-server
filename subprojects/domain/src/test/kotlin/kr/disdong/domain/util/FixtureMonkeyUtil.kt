package kr.disdong.domain.util

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.LabMonkeyBuilder
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin

class FixtureMonkeyUtil {

    companion object {
        fun work(): LabMonkeyBuilder {
            return FixtureMonkey.labMonkeyBuilder()
                .plugin(KotlinPlugin())
        }
    }
}
