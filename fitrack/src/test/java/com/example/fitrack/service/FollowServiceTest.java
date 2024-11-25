package com.example.fitrack.service;

import com.example.fitrack.models.Follow;
import com.example.fitrack.models.User;
import com.example.fitrack.repository.IFollowRepository;
import com.example.fitrack.repository.IUserRepository;
import com.example.fitrack.services.FollowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class FollowServiceTest {

    @Mock
    private IFollowRepository followRepository;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private FollowService followService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFollowUser() {
        Long userId = 1L;
        Long followerId = 2L;

        User user = new User();
        user.setId(userId);

        User follower = new User();
        follower.setId(followerId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findById(followerId)).thenReturn(Optional.of(follower));

        followService.followUser(userId, followerId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).findById(followerId);
        verify(followRepository, times(1)).save(any(Follow.class));
    }
}
