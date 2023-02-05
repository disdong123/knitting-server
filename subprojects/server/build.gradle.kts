dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    // implementation("io.springfox:springfox-boot-starter:3.0.0")
    // implementation("io.springfox:springfox-swagger-ui:3.0.0")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation(project(":subprojects:domain-jpa"))
    implementation(project(":subprojects:auth"))
    implementation(project(":subprojects:common"))
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
