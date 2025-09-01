package io.goods.bhgoods.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Mapeia propriedades JWT customizadas.
 * Prefixo: app.jwt
 * Exemplo no application.properties:
 * app.jwt.secret-base64=...
 * app.jwt.expiration-ms=86400000
 */
@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

    /**
     * Segredo Base64 (32 bytes = 256 bits) para HMAC-SHA256.
     */
    private String secretBase64 = "bXlTZWNyZXRLZXk="; // default fraco; substituir via env

    /**
     * Expiração em milissegundos (default 24h).
     */
    private long expirationMs = 86400000L;

    public String getSecretBase64() {
        return secretBase64;
    }

    public void setSecretBase64(String secretBase64) {
        this.secretBase64 = secretBase64;
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    public void setExpirationMs(long expirationMs) {
        this.expirationMs = expirationMs;
    }
}
