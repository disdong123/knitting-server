dependencies {
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-aws
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

    implementation(project(":knitting-mysql"))
    implementation(project(":knitting-common"))
}
