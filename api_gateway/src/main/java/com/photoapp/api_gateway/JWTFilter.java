package com.photoapp.api_gateway;

import com.google.common.net.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

@Component
public class JWTFilter extends AbstractGatewayFilterFactory<JWTFilter.Config> {

  @Autowired
  Environment env;

  public JWTFilter() {
    super(Config.class);
  }

  public static class Config {
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      ServerHttpRequest req = exchange.getRequest();
      if (!req.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
        return onError(exchange, "Missing Authorization Header", HttpStatus.UNAUTHORIZED);
      }
      String authHeader = req.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
      String jwt = authHeader.substring(7);
      if (!isJWTValid(jwt)) {
        return onError(exchange, "Invalid JWT", HttpStatus.UNAUTHORIZED);
      }
      return chain.filter(exchange);
    };
  }

  private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
    ServerHttpResponse res = exchange.getResponse();
    res.setStatusCode(status);
    return res.setComplete();
  }

  private boolean isJWTValid(String jwt) {
    try {
      String subject = Jwts.parser().setSigningKey(env.getProperty("token.secret")).parseClaimsJws(jwt).getBody()
          .getSubject();
      if (subject == null || subject.isEmpty()) {
        return false;
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
      return false;
    }
    return true;
  }
}
