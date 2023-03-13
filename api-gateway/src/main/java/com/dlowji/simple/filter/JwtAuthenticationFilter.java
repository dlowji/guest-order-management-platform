package com.dlowji.simple.filter;

import com.dlowji.simple.exception.JwtTokenMalformedException;
import com.dlowji.simple.exception.JwtTokenMissingException;
import com.dlowji.simple.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        final List<String> apiEndpoints = List.of("/register", "/login", "/logout");

        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                .noneMatch(uri -> r.getURI().getPath().contains(uri));

        if (isApiSecured.test(request)) {
            if (!request.getHeaders().containsKey("Authorization")) {
                Map<String, Object> responseMap = new LinkedHashMap<>();
                responseMap.put("code", 401);
                responseMap.put("message", "Unauthorized");
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

                try {
                    DataBuffer buffer = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(responseMap));
                    return response.writeWith(Mono.just(buffer));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            final String token = request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
            try {
                jwtUtil.validateToken(token);
            } catch (JwtTokenMalformedException | JwtTokenMissingException e) {
                ServerHttpResponse response = exchange.getResponse();
                Map<String, Object> responseMap = new LinkedHashMap<>();
                responseMap.put("code", 401);
                responseMap.put("message", "Invalid token");
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                try {
                    DataBuffer buffer = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(responseMap));
                    return response.writeWith(Mono.just(buffer));
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            }

            Claims claims = jwtUtil.getClaims(token);
            exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
        }

        return chain.filter(exchange);
    }
}
