package com.dlowji.simple.filter;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class CorsFilter implements WebFilter {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpResponse response = exchange.getResponse();
		response.getHeaders().set("Access-Control-Allow-Origin", "*");
		System.out.println(response.getHeaders().getAccessControlAllowOrigin());
		response.getHeaders().add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
		response.getHeaders().add("Access-Control-Max-Age", "3600");
		if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
			response.setStatusCode(HttpStatus.OK);
			response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
			return Mono.empty();
		}
		return chain.filter(exchange);
	}
}