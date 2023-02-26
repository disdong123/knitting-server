dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation("com.google.code.gson:gson:2.10.1")

    implementation(project(":knitting-common"))
    implementation(project(":knitting-domain-jpa"))
}
