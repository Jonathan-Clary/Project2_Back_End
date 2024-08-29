package com.revature.config;

import com.revature.security.PasswordEncoderProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // User-specific JWT filter to handle token-based authentication
    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean// This ensures the user chain is evaluated after the admin chain
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Disable CSRF configuration (not needed for a stateless API)
        httpSecurity.csrf(customizer -> customizer.disable());

        // Define user-specific authorization rules for HTTP requests
        httpSecurity.authorizeHttpRequests(request -> {
            request
                    // Allow unrestricted access to user registration and login endpoints
                    .requestMatchers(HttpMethod.POST, "/user/register", "/auth/login").permitAll()
                    // Require authentication for all other user-specific /user/** URLs
                    //.requestMatchers("/user/**").authenticated()
                    .anyRequest().authenticated();
                    // Deny all other requests outside user-specific /user/**
                    //.anyRequest().denyAll();
        });

        // Set session management to stateless (no session will be created)
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add the user-specific JWT filter before the default username/password authentication filter
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // Build and return the user-specific SecurityFilterChain
        return httpSecurity.build();
    }

    // User-specific service to load user data
    @Autowired
    UserDetailsService userDetailsService;

    // Password encoder password encryption
    @Autowired
    PasswordEncoderProvider passwordEncoder;

    // Bean that provides the user-specific authentication mechanism based on user details and password encoder
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // Set the password encoder
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        // Set the user-specific user details service
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        // Return the user-specific authentication provider
        return daoAuthenticationProvider;
    }


    @Bean()
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
