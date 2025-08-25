package io.goods.bhgoods.config;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.goods.bhgoods.model.User;
import io.goods.bhgoods.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Filter that runs once per request to check for JWT token
// OncePerRequestFilter ensures it runs only once even if there are internal forwards/includes
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    // Main filter method - called for every HTTP request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getTokenFromRequest(request);
    
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            String email = tokenProvider.getEmailFromToken(token);
            String role = tokenProvider.getRoleFromToken(token);
            
            Optional<User> userOptional = userRepository.findByEmail(email);
            
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                // Create authentication token with user data and authorities
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                        user,   // Principal: the authenticated user object
                        null,   // Credentials: null because we already validated the JWT
                        java.util.Collections.singletonList(  // Authorities: user's role
                            new SimpleGrantedAuthority("ROLE_" + role)  // Spring Security expects "ROLE_" prefix
                        )
                    );
                
                // Set additional details (IP, session ID, etc.)
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Step 5: Set authentication in Spring Security context
                // This tells Spring Security that the user is authenticated
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Log security incident: valid JWT token but user doesn't exist in database
                logger.warn("Valid JWT token found for non-existent user: {}", email);
            }
        }
        
        // Step 6: Continue with the request chain
        // Important: always call this to let the request proceed
        filterChain.doFilter(request, response);
    }

    // Helper method to extract token from Authorization header
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");  // Get Authorization header
        
        // Check if header exists and starts with "Bearer "
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Remove "Bearer " prefix, return just the token
        }
        return null;  // No valid token found
    }
}
