package com.vmsapi.security;

import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String JWKS_URL = "https://cognito-idp.us-east-1.amazonaws.com/us-east-1_Nj7xV6STB/.well-known/jwks.json";
    private final ConfigurableJWTProcessor<SecurityContext> jwtProcessor;

    public JwtAuthenticationFilter() throws MalformedURLException {
        jwtProcessor = new DefaultJWTProcessor<>();
        try {
            JWSKeySelector<SecurityContext> keySelector = new com.nimbusds.jose.proc.JWSVerificationKeySelector<>(
                    com.nimbusds.jose.JWSAlgorithm.RS256,
                    new RemoteJWKSet<>(new URL(JWKS_URL))
            );
            jwtProcessor.setJWSKeySelector(keySelector);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize JWKS key selector", e);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                JWTClaimsSet claims = jwtProcessor.process(token, null);
                String username = claims.getStringClaim("preferred_username");
                if (username == null || username.isEmpty()) {
                    username = claims.getStringClaim("custom:username");
                }
                if (username == null || username.isEmpty()) {
                    username = claims.getStringClaim("cognito:username");
                }
                String role = claims.getStringClaim("custom:role");
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Collections.emptyList()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
                request.setAttribute("role", role);
                request.setAttribute("username", username);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
