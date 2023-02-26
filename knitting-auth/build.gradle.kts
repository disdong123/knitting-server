dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.validation)
    implementation("com.google.code.gson:gson:2.10.1")

    implementation(project(":knitting-common"))
    implementation(project(":knitting-mysql"))
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
