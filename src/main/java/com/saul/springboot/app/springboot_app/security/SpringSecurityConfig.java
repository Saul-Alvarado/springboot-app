package com.saul.springboot.app.springboot_app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.saul.springboot.app.springboot_app.security.filter.JwtAuthenticationFilter;
import com.saul.springboot.app.springboot_app.security.filter.JwtValidationFilter;

@Configuration
public class SpringSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    public SpringSecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    AuthenticationManager authenticationManager () throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder poPasswordEncoder (){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests((authz) -> authz
        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/{id}").permitAll()
        // .requestMatchers(HttpMethod.POST, "/api/products").hasRole("ADMIN")
        // .requestMatchers(HttpMethod.PUT, "/api/products/{id}").hasRole("ADMIN")
        // .requestMatchers(HttpMethod.DELETE, "/api/products/{id}").hasRole("ADMIN")
        // .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
        // .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
        .anyRequest().authenticated())
        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
        .addFilter(new JwtValidationFilter(authenticationManager()))
        .csrf(config -> config.disable())
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
    }

}
