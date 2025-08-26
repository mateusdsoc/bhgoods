package io.goods.bhgoods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.goods.bhgoods.dto.JwtAuthenticationResponse;
import io.goods.bhgoods.dto.LoginRequest;
import io.goods.bhgoods.dto.Restaurante.RegisterRestauranteRequest;
import io.goods.bhgoods.model.User;
import io.goods.bhgoods.service.AuthService;
import jakarta.validation.Valid;

// REST Controller for authentication endpoints
@RestController
@RequestMapping("/api/auth")      
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            JwtAuthenticationResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);  // HTTP 200 with token response
        } catch (RuntimeException e) {  
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED) //401 unauthorized
                    .body("Erro de autenticação: " + e.getMessage());
        }
    }

    @PostMapping("/register/restaurante")
    public ResponseEntity<?> registerRestaurante(@Valid @RequestBody RegisterRestauranteRequest registerRequest) {
        try {
            // @Valid triggers validation annotations in RegisterRestauranteRequest
            JwtAuthenticationResponse response = authService.registerRestaurante(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) //400 bad request
                    .body("Erro no cadastro: " + e.getMessage());
        }
    }

    // Get current user endpoint - GET /api/auth/me
    // This endpoint requires authentication (JWT token)
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            // Get authentication from Spring Security context
            // This context was set by our JwtAuthenticationFilter
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            // Check if user is authenticated and principal is our User object
            if (authentication != null && authentication.getPrincipal() instanceof User) {
                User user = (User) authentication.getPrincipal();  // Cast principal to User
                return ResponseEntity.ok(user);  // Return user data
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno: " + e.getMessage());
        }
    }
}
