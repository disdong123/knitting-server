package kr.disdong.domain.domain

import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.navercorp.fixturemonkey.kotlin.setExp
import kr.disdong.domain.util.FixtureMonkeyUtil
import kr.disdong.knitting.domain.jpa.domain.PostEntity

class PostEntityFixture {
    companion object {
        fun any(title: String = "default"): PostEntity {
            return FixtureMonkeyUtil.work()
                .build()
                .giveMeBuilder<PostEntity>()
                .setExp(PostEntity::title, title)
                .sample()
        }
    }
}
