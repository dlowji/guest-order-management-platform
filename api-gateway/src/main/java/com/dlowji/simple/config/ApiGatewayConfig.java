package com.dlowji.simple.config;

import com.dlowji.simple.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {
    private final JwtAuthenticationFilter filter;

    public ApiGatewayConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("accounts", r -> r.path("/accounts/**").filters(f -> f.filter(filter)).uri("http://localhost:8083"))
                .build();
    }
}
