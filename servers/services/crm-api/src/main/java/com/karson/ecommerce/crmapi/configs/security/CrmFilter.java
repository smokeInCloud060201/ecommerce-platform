package com.karson.ecommerce.crmapi.configs.security;

import com.karson.ecommerce.common.configs.sercurity.context.AuthModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class CrmFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TYPE = "Bearer ";
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull jakarta.servlet.FilterChain filterChain) throws ServletException, IOException {
        final String authorizeHeader = request.getHeader(AUTHORIZATION_HEADER);
        final String userName;
        final String token;

        if (null == authorizeHeader) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean isJWTAuthenticate = authorizeHeader.startsWith(BEARER_TYPE);

        if (isJWTAuthenticate) {
            token = authorizeHeader.substring(7);

            if (!token.isBlank()) {
                userName = jwtService.extractUsername(token);
                if (userName != null && !userName.isBlank()) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                    if (jwtService.isTokenValid(token, userDetails)) {
                        AuthModel contextModel = jwtService.extractContextModel(token);
                        UsernamePasswordAuthenticationToken context = new UsernamePasswordAuthenticationToken(
                                contextModel.getUserPrincipal(), null, contextModel.getGrantedAuthoritySet()
                        );
                        SecurityContextHolder.getContext().setAuthentication(context);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
