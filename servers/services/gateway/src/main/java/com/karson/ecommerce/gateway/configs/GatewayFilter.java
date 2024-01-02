package com.karson.ecommerce.gateway.configs;


import com.karson.ecommerce.gateway.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class GatewayFilter implements GlobalFilter {

    private final RouterValidate routerValidate;
    private final JwtService jwtService;


    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER = "Bearer";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (!routerValidate.isSecured(request)) {
            if (isAuthMissing(request)) {
                return onError(exchange, "Authorization header is missing in request");
            }

            final String token = this.getAuthHeader(request);

            if (jwtService.isTokenInvalid(token))
                return onError(exchange, "Authorization header is invalid");

            this.populateRequestWithHeaders(exchange, token);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err) {
        log.error(err);
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(AUTHORIZATION_HEADER).get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(AUTHORIZATION_HEADER);
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        exchange.getRequest().mutate()
                .header(AUTHORIZATION_HEADER, token);
    }
}
