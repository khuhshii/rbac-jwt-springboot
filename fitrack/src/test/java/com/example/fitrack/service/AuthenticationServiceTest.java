package com.example.fitrack.service;
import com.example.fitrack.dto.AuthenticationResponse;
import com.example.fitrack.dto.UserDTO;
import com.example.fitrack.models.Role;
import com.example.fitrack.models.User;
import com.example.fitrack.repository.IUserRepository;
import com.example.fitrack.services.AuthenticationService;
import com.example.fitrack.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @Mock
    private IUserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setRole(Role.USER);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(repository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.register(user);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    public void testAuthenticate() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("password");

        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("encodedPassword");

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.authenticate(userDTO);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(authenticationManager, times(1)).authenticate(any());
    }
}

