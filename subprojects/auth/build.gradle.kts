dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation(project(":subprojects:common"))
}
//
// tasks.getByName("bootJar") {
//     enabled = true
// }
//
// tasks.getByName("jar") {
//     enabled = false
// }
