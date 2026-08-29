package com.saul.springboot.app.springboot_app.security.filter;

import static com.saul.springboot.app.springboot_app.security.TokenJwtConfig.CONTENT_TYPE;
import static com.saul.springboot.app.springboot_app.security.TokenJwtConfig.HEAD_AUTHORIZATION;
import static com.saul.springboot.app.springboot_app.security.TokenJwtConfig.PREFIX_TOKEN;
import static com.saul.springboot.app.springboot_app.security.TokenJwtConfig.SECRET_KEY;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.saul.springboot.app.springboot_app.security.SimpleGrantedAuthorityJsonCreator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;


public class JwtValidationFilter extends BasicAuthenticationFilter{

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(HEAD_AUTHORIZATION);

        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(PREFIX_TOKEN, "");

        try {
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
            String username = claims.getSubject();

            Object authoritiesClaims = claims.get("authorities");

            ObjectMapper mapper = JsonMapper.builder()
                .addMixIn(SimpleGrantedAuthority.class,SimpleGrantedAuthorityJsonCreator.class)
                .build();

        
            Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                mapper.readValue(
                    authoritiesClaims.toString().getBytes(),
                    SimpleGrantedAuthority[].class
                )
            );

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder .getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);

        } catch (JwtException e) {
            
            Map<String, String> body = new HashMap<>();
            body.put("message", "Token JWT No valido");
            body.put("error", e.getMessage());

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(CONTENT_TYPE);
        }

    }

    

}
