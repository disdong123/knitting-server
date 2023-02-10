rootProject.name = "knitting-server"

include(
    "subprojects:server",
    "subprojects:domain-jpa",
    "subprojects:auth",
    "subprojects:common"
)

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.version.toml"))
        }
    }
}
