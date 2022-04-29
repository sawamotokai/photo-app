package com.photoapp.api_gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class MyPreFilter implements GlobalFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String path = exchange.getRequest().getPath().toString();
    System.out.println(path);
    HttpHeaders headers = exchange.getRequest().getHeaders();
    headers.keySet().forEach(key -> {
      System.out.println(key + ": " + headers.get(key));
    });
    return chain.filter(exchange);
  }

}
