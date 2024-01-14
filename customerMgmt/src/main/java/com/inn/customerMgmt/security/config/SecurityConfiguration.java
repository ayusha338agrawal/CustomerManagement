package com.inn.customerMgmt.security.config;

import static com.inn.customerMgmt.security.user.Permission.ADMIN_CREATE;
import static com.inn.customerMgmt.security.user.Permission.ADMIN_DELETE;
import static com.inn.customerMgmt.security.user.Permission.ADMIN_READ;
import static com.inn.customerMgmt.security.user.Permission.ADMIN_UPDATE;
import static com.inn.customerMgmt.security.user.Role.ADMIN;
import static com.inn.customerMgmt.security.user.Role.USER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/v2/**").hasAnyRole(ADMIN.name(),USER.name())
                                .requestMatchers("/api/**").hasAnyRole(ADMIN.name(),USER.name())
                                .requestMatchers(GET, "/api/**").hasAnyAuthority(ADMIN_READ.name(),USER.name())
                                .requestMatchers(POST, "/api/**").hasAnyAuthority(ADMIN_CREATE.name(),USER.name())
                                .requestMatchers(PUT, "/api/**").hasAnyAuthority(ADMIN_UPDATE.name(),USER.name())
                                .requestMatchers(DELETE, "/api/v1/**").hasAnyAuthority(ADMIN_DELETE.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .logout(logout ->
//                        logout.logoutUrl("/api/v1/auth/logout")
//                                .addLogoutHandler(logoutHandler)
//                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
//                )
        

        return http.build();
    }
}
