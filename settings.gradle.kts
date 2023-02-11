rootProject.name = "knitting-server"

include(
    "subprojects:server",
    "subprojects:domain-jpa",
    "subprojects:auth",
    "subprojects:common"
)

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    fun ExtraPropertiesExtension.findProperty(name: String): String? {
        return if (has(name)) {
            get(name) as String?
        } else {
            null
        }
    }

    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/disdong123/version-catalog")
            credentials {
                username = extra.findProperty("gpr.user") ?: System.getenv("DISDONG_USERNAME")
                password = extra.findProperty("gpr.key") ?: System.getenv("DISDONG_TOKEN")
            }
        }
    }
    versionCatalogs {
        create("libs") {
            from("kr.disdong:spring-version-catalog:0.0.2")
        }
    }
}
