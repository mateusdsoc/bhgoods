package io.goods.bhgoods.dto;

import io.goods.bhgoods.enums.UserRole;

// DTO for authentication response - what we send back after successful login
public class JwtAuthenticationResponse {
    
    private String accessToken;      // The JWT token itself
    private String tokenType = "Bearer";  // Token type (always "Bearer" for JWT)
    private Long userId;
    private String email;
    private UserRole role;
    
    public JwtAuthenticationResponse() {}
    
    // used when creating response
    public JwtAuthenticationResponse(String accessToken, Long userId, String email, UserRole role) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.email = email;
        this.role = role;
    }
    
    // Getters and Setters
    public String getAccessToken() { 
        return accessToken; 
    }
    
    public void setAccessToken(String accessToken) { 
        this.accessToken = accessToken; 
    }
    
    public String getTokenType() { 
        return tokenType; 
    }
    
    public void setTokenType(String tokenType) { 
        this.tokenType = tokenType; 
    }
    
    public Long getUserId() { 
        return userId; 
    }
    
    public void setUserId(Long userId) { 
        this.userId = userId; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public UserRole getRole() { 
        return role; 
    }
    
    public void setRole(UserRole role) { 
        this.role = role; 
    }
}
