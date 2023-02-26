@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.plugin.jpa)
}

dependencies {
    // TODO. 확인 필요. allOpen 없어도 됩니다.?
    api(libs.spring.boot.starter.data.jpa)
    api(libs.hibernate.types)
    api(libs.infobip.spring.data.jpa.querydsl.boot.starter)
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    runtimeOnly(libs.mysql.connector.java)

    implementation(project(":knitting-common"))
}
