package kr.disdong.knitting.server.core.config

import kr.disdong.knitting.server.core.interceptor.AuthInterceptor
import kr.disdong.knitting.server.core.resolver.CurrentUserResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    @Autowired
    private lateinit var authInterceptor: AuthInterceptor

    @Autowired
    private lateinit var currentUserResolver: CurrentUserResolver

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver?>) {
        resolvers.add(currentUserResolver)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/api/docs.html")
            .excludePathPatterns("/api/swagger-ui/index.html")
    }
}
