package io.goods.bhgoods.config;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.goods.bhgoods.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// Component that handles JWT token creation and validation
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    // Creates signing key from the secret string
    // Base64 decodes the secret and creates HMAC SHA key
    private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretBase64());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generates JWT token for a given user
    public String generateToken(User user) {
        Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtProperties.getExpirationMs());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extracts email from JWT token
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())  // Use our secret key to verify signature
                .build()
                .parseClaimsJws(token)           // Parse and validate the token
                .getBody();
        return claims.getSubject();              // Subject contains the email
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Long.class);
    }

    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }

    // Validates if token is valid (not expired, not tampered with)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token); //throw exception if invalid
            return true;
        } catch (Exception ex) {
            return false;  // Any exception means invalid token
        }
    }
}
