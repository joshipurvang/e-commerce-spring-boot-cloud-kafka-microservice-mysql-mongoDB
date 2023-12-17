package com.eos.boot;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter implements GlobalFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();

		log.info("Request URI: {} ", request.getPath());
		log.info("Request Header: {} ", request.getHeaders());
				
		return chain.filter(exchange);
	}
	
	@Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange)
              .then(Mono.fromRunnable(() -> {
            	  ServerHttpResponse response = exchange.getResponse();
            	log.info("Response Code: {} ", response.getStatusCode());
          		log.info("Response Header: {} ", response.getHeaders());
          		 
          		
              }));
        };
        
    }
	
	
	

}
