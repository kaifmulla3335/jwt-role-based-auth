package com.example.authdemo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;      // Loaded from application.properties

    @Value("${jwt.expiration}")
    private long jwtExpiration;    // 86400000 = 24 hours

    // ─── Generate Token ───────────────────────────────────────────────

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        // Add role to token payload so React can read it
        extraClaims.put("role", userDetails.getAuthorities()
                .iterator().next().getAuthority());

        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())   // "username" = email in our case
                .issuedAt(new Date())                 // When token was created
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Expiry
                .signWith(getSigningKey())             // Sign with secret key
                .compact();                            // Build the token string
    }

    // ─── Validate Token ───────────────────────────────────────────────

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // Token is valid if: email matches AND token is not expired
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // ─── Extract Data from Token ──────────────────────────────────────

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);  // Subject = email
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic method to extract any claim from token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())   // Use secret key to verify signature
                .build()
                .parseSignedClaims(token)
                .getPayload();                 // Get the data part
    }

    // Convert secret string to a cryptographic key
    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}