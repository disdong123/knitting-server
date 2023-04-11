dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.validation)

    implementation(project(":knitting-auth"))
    implementation(project(":knitting-mysql"))
    implementation(project(":knitting-common"))
    implementation(project(":knitting-storage"))
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
