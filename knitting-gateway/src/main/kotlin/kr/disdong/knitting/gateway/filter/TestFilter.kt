package kr.disdong.knitting.gateway.filter

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class CustomFilter : AbstractGatewayFilterFactory<CustomFilter.Config>(Config::class.java) {
    override fun apply(config: Config): GatewayFilter {
        println("?")
        // Custom Pre Filter
        return GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            println("??")
            val request: ServerHttpRequest = exchange.request
            val response: ServerHttpResponse = exchange.response
            chain.filter(exchange).then(Mono.fromRunnable {
                println("???")
            })
        }
    }

    class Config { // Put the configuration properties
    }
}
