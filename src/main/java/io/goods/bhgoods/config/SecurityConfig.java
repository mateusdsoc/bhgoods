package io.goods.bhgoods.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Main Spring Security configuration class
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)  // Enables @PreAuthorize and @PostAuthorize annotations
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // Bean for password encoding - BCrypt is secure hashing algorithm
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCrypt with default strength (10 rounds)
    }

    // Bean for authentication manager - handles authentication logic
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Main security configuration - defines which endpoints are protected
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Configure authorization rules for different endpoints
            .authorizeHttpRequests(authz -> authz
                // PUBLIC ENDPOINTS - anyone can access these
                .requestMatchers("/api/auth/**").permitAll()      // Login/register endpoints
                .requestMatchers("/api/public/**").permitAll()    // Any public endpoints
                
                // ROLE-BASED ENDPOINTS - only specific roles can access
                .requestMatchers("/api/admin/**").hasRole("ADMIN")           // Admin only
                .requestMatchers("/api/restaurante/**").hasRole("RESTAURANTE") // Restaurant only
                
                // ALL OTHER ENDPOINTS - require authentication but any role
                .anyRequest().authenticated()
            )
            
            // Add our JWT filter before Spring's default authentication filter
            // This ensures JWT tokens are processed before standard username/password
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();  // Build and return the security configuration
    }
}
