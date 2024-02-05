package com.microservices.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {

		return builder.routes()
				.route(p -> p
						.path("/product-service/**")
						.filters(f -> f.rewritePath("/product-service/(?<remaining>.*)", "/${remaining}"))
						.uri("lb://product-service")
				)
				.route(p -> p
						.path("/order-service/**")
						.filters(f -> f.rewritePath("/order-service/(?<remaining>.*)", "/${remaining}"))
						.uri("lb://order-service")
				)
				.route(p -> p
						.path("/inventory-service/**")
						.filters(f -> f.rewritePath("/inventory-service/(?<remaining>.*)", "/${remaining}"))
						.uri("lb://inventory-service")
				)
				.route(p -> p
						.path("/payments-service/**")
						.filters(f -> f.rewritePath("/payments-service/(?<remaining>.*)", "/${remaining}"))
						.uri("lb://payments-service")
				)
				.build();
	}
}
