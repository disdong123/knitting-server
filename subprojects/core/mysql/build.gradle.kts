plugins {
    kotlin("plugin.jpa")
}

dependencies {
    // TODO. 확인 필요. allOpen 없어도 됩니다.?
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("com.vladmihalcea:hibernate-types-60:2.20.0")
    api("com.infobip:infobip-spring-data-jpa-querydsl-boot-starter:8.0.0")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    runtimeOnly("mysql:mysql-connector-java")
}
