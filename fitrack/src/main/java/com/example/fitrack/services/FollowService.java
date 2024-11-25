package com.example.fitrack.services;

import com.example.fitrack.models.Follow;
import com.example.fitrack.models.User;
import com.example.fitrack.repository.IFollowRepository;
import com.example.fitrack.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    @Autowired
    private IFollowRepository followRepository;
    @Autowired
    private IUserRepository userRepository;

    public void followUser(Long userId, Long followerId) {
        User user = userRepository.findById(userId).orElseThrow();
        User follower = userRepository.findById(followerId).orElseThrow();
        Follow follow = new Follow();
        follow.setUser(user);
        follow.setFollower(follower);
        followRepository.save(follow);
    }

}
