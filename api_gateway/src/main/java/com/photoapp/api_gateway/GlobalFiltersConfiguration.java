package com.photoapp.api_gateway;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;

@Configuration
public class GlobalFiltersConfiguration {
  @Bean
  public GlobalFilter secondPreFilter() {
    return (exchange, chain) -> {
      System.out.println("Second Pre Filter");
      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        System.out.println("Second Post Filter");
      }));
    };
  }

  @Bean
  public GlobalFilter thirdPreFilter() {
    return (exchange, chain) -> {
      System.out.println("third Pre Filter");
      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        System.out.println("third Post Filter");
      }));
    };
  }

  @Bean
  public GlobalFilter forthPreFilter() {
    return (exchange, chain) -> {
      System.out.println("forth Pre Filter");
      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        System.out.println("forth Post Filter");
      }));
    };
  }
}