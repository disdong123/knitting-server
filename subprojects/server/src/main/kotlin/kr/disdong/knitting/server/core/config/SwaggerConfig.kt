package kr.disdong.knitting.server.core.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class SwaggerConfig {
    @Bean
    fun openAPI(@Value("v1.0") appVersion: String?): OpenAPI {
        val info = Info()
            .title("KNITTING!!  KNITTING!!  KNITTING!!")
            .version(appVersion)
            .description("KNITTING!! KNITTING!! KNITTING!!")

        // 인증
        val security = SecurityRequirement().addList("AccessToken")

        val components = Components()
            .addSecuritySchemes(
                "AccessToken",
                SecurityScheme()
                    .name("AccessToken")
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            )

        return OpenAPI()
            .info(info)
            .addSecurityItem(security)
            .components(components)
    }
}
