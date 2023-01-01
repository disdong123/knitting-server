dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation(project(":subprojects:core:mysql"))
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
