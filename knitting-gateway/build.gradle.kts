extra["springCloudVersion"] = "2022.0.1"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

dependencies {
    implementation(project(":knitting-common"))

    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-eureka-server
    // implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
    // https://mvnrepository.com/artifact/io.netty/netty-resolver-dns-native-macos
    // implementation("io.netty:netty-resolver-dns-native-macos:4.1.89.Final")
    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.76.Final:osx-aarch_64")
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
