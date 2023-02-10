dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.validation)

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
