package com.karson.ecommerce.crmapi.configs.security;

import com.karson.ecommerce.common.configs.sercurity.context.AuthModel;
import com.karson.ecommerce.common.configs.sercurity.context.ContextModel;
import com.karson.ecommerce.common.utils.JsonUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final SecurityConfigProperties securityConfigProperties;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public AuthModel extractContextModel(String token) {
        try {
            Map<String, Object> tokenMaps = JsonUtil.convertToMap(extractClaim(token, claims -> claims.get("payload")));
            Map<String, Object> userPrincipalMaps = JsonUtil.convertToMap(tokenMaps.get("userPrincipal"));
            Set<String> roleSet = JsonUtil.convertToSet(userPrincipalMaps.get("roles"));
            Set<String> permissionSet = JsonUtil.convertToSet(userPrincipalMaps.get("permissions"));
            String email =  (String) userPrincipalMaps.get("email");
            Set<GrantedAuthority> grantedAuthoritySet = permissionSet.stream()
                    .map(per -> new SimpleGrantedAuthority("SCOPE_" + per))
                    .collect(Collectors.toSet());
            return AuthModel.builder()
                            .userPrincipal(AuthModel.UserPrincipal.builder()
                                    .email(email)
                                    .roles(roleSet)
                                    .permissions(permissionSet)
                                    .isEnabled((Boolean) userPrincipalMaps.get("enabled"))
                                    .isCredentialsNonExpired((Boolean) userPrincipalMaps.get("credentialsNonExpired"))
                                    .isAccountNonLocked((Boolean) userPrincipalMaps.get("accountNonLocked"))
                                    .isAccountNonExpired((Boolean) userPrincipalMaps.get("accountNonExpired"))
                                    .build())
                            .grantedAuthoritySet(grantedAuthoritySet)
                            .build();

        } catch (Exception e) {
            log.error("Error when parse token to context model : {}", e.getMessage());
        }
        return null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(ContextModel contextModel) {
        return generateToken(new HashMap<>(), contextModel);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            ContextModel contextModel
    ) {
        return buildToken(extraClaims, contextModel, securityConfigProperties.getAccessTokenExpiration());
    }

    public String generateRefreshToken(
            ContextModel contextModel
    ) {
        return buildToken(new HashMap<>(), contextModel, securityConfigProperties.getRefreshTokenExpiration());
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            ContextModel contextModel,
            long expiration
    ) {
        extraClaims.put("payload", contextModel.getAuthModel());

        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS512");
        headers.put("typ", "JWT");

        return Jwts.builder()
                .setClaims(extraClaims)
                .setHeader(headers)
                .setIssuer(securityConfigProperties.getIss())
                .setSubject(contextModel.getAuthModel().getUserPrincipal().getEmail())
                .setAudience(String.join(", ", securityConfigProperties.getAuds()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey()).build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(securityConfigProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
