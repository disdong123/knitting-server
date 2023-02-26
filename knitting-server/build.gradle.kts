dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.validation)

    implementation(project(":knitting-domain-jpa"))
    implementation(project(":knitting-auth"))
    implementation(project(":knitting-common"))
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}