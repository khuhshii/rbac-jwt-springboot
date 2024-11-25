package com.example.fitrack.service;
import com.example.fitrack.models.Role;
import com.example.fitrack.models.User;
import com.example.fitrack.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private String token;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setRole(Role.USER);
        token = jwtService.generateToken(user);
    }

    @Test
    public void testExtractEmail() {
        String email = jwtService.extractEmail(token);
        assertEquals("user@example.com", email);
    }

    @Test
    public void testIsValid() {
        when(userDetails.getUsername()).thenReturn("user@example.com");
        boolean isValid = jwtService.isValid(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    public void testIsTokenExpired() {
        boolean isExpired = jwtService.isTokenExpired(token);
        assertFalse(isExpired);
    }

    @Test
    public void testExtractExpiration() {
        Date expiration = jwtService.extractExpiration(token);
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    public void testExtractAllClaims() {
        Claims claims = jwtService.extractAllClaims(token);
        assertNotNull(claims);
        assertEquals("user@example.com", claims.getSubject());
    }

    @Test
    public void testGenerateToken() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setRole(Role.USER);
        String generatedToken = jwtService.generateToken(user);
        assertNotNull(generatedToken);
    }
}
