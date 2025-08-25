package io.goods.bhgoods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.goods.bhgoods.config.JwtTokenProvider;
import io.goods.bhgoods.dto.JwtAuthenticationResponse;
import io.goods.bhgoods.dto.LoginRequest;
import io.goods.bhgoods.dto.RegisterRestauranteRequest;
import io.goods.bhgoods.enums.StatusAprovacao;
import io.goods.bhgoods.model.Restaurante;
import io.goods.bhgoods.model.User;
import io.goods.bhgoods.repository.UserRepository;

// Service class containing authentication business logic
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;    // To interact with database

    @Autowired
    private PasswordEncoder passwordEncoder;  // To hash and verify passwords

    @Autowired
    private JwtTokenProvider tokenProvider;   // To generate JWT tokens

    // Login method - authenticates user and returns JWT token
    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        // Step 1: Find user by email using record accessor method
        User user = userRepository.findByEmail(loginRequest.email())  // Record accessor: email()
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Step 2: Verify password using record accessor method
        // passwordEncoder.matches() compares plain text password with hashed password
        if (!passwordEncoder.matches(loginRequest.senha(), user.getSenha())) {  // Record accessor: senha()
            throw new RuntimeException("Credenciais inválidas");
        }

        // Step 3: Generate JWT token for authenticated user
        String token = tokenProvider.generateToken(user);
        
        // Step 4: Return response with token and user info
        return new JwtAuthenticationResponse(token, user.getId(), user.getEmail(), user.getRole());
    }

    // Registration method for restaurants
    public JwtAuthenticationResponse registerRestaurante(RegisterRestauranteRequest registerRequest) {
        // Step 1: Check if email is already in use
        if (userRepository.existsByEmail(registerRequest.email())) {  // Using record accessor method
            throw new RuntimeException("Email já está em uso");
        }

        // Step 2: Create new Restaurante entity
        Restaurante restaurante = new Restaurante();
        restaurante.setEmail(registerRequest.email());               // Record accessor: email()
        
        // Hash the password before storing - NEVER store plain text passwords
        restaurante.setSenha(passwordEncoder.encode(registerRequest.senha()));  // Record accessor: senha()
        
        // Set restaurant-specific fields using record accessor methods
        restaurante.setNome(registerRequest.nome());                // Record accessor: nome()
        restaurante.setDescricao(registerRequest.descricao());      // Record accessor: descricao()
        restaurante.setEndereco(registerRequest.endereco());        // Record accessor: endereco()
        restaurante.setTelefone(registerRequest.telefone());        // Record accessor: telefone()
        
        // New restaurants start as pending approval
        restaurante.setStatusAprovacao(StatusAprovacao.PENDENTE);

        // Step 3: Save to database - role will be automatically set to RESTAURANTE
        User savedUser = userRepository.save(restaurante);
        
        // Step 4: Generate token for newly registered user
        String token = tokenProvider.generateToken(savedUser);
        
        // Step 5: Return response with token and user info
        return new JwtAuthenticationResponse(token, savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
    }

    // Helper method to get current user by email
    // Used by controllers to get full user data from security context
    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
