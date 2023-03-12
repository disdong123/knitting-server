dependencies {
    implementation(project(":knitting-common"))

    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.76.Final:osx-aarch_64")
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
