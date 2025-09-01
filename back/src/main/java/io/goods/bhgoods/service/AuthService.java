package io.goods.bhgoods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.goods.bhgoods.config.JwtTokenProvider;
import io.goods.bhgoods.dto.JwtAuthenticationResponse;
import io.goods.bhgoods.dto.LoginRequest;
import io.goods.bhgoods.dto.Restaurante.RegisterRestauranteRequest;
import io.goods.bhgoods.enums.StatusAprovacao;
import io.goods.bhgoods.model.Restaurante;
import io.goods.bhgoods.model.User;
import io.goods.bhgoods.repository.UserRepository;

// Service class containing authentication business logic
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // Login method - authenticates user and returns JWT token
    public JwtAuthenticationResponse login(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginRequest.senha(), user.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = tokenProvider.generateToken(user);
        
        //Return response with token and user info
        return new JwtAuthenticationResponse(token, user.getId(), user.getEmail(), user.getRole());
    }

    // Registration method for restaurants
    public JwtAuthenticationResponse registerRestaurante(RegisterRestauranteRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new RuntimeException("Email já está em uso");
        }

        Restaurante restaurante = new Restaurante();
        restaurante.setEmail(registerRequest.email());
        
        // Hash the password before storing - NEVER store plain text passwords
        restaurante.setSenha(passwordEncoder.encode(registerRequest.senha()));
        
        // Set restaurant-specific fields using record accessor methods
        restaurante.setNome(registerRequest.nome());
        restaurante.setDescricao(registerRequest.descricao());
        restaurante.setEndereco(registerRequest.endereco());
        restaurante.setTelefone(registerRequest.telefone());

        restaurante.setStatusAprovacao(StatusAprovacao.PENDENTE);

        User savedUser = userRepository.save(restaurante);
        
        String token = tokenProvider.generateToken(savedUser);
        
        //Return response with token and user info
        return new JwtAuthenticationResponse(token, savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
    }

    // Helper method to get current user by email
    // Used by controllers to get full user data from security context
    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
}
