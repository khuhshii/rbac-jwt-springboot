package com.example.fitrack.controllers;

import com.example.fitrack.dto.request.FollowRequest;
import com.example.fitrack.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/users/{userId}/follow")
    public ResponseEntity<String> followUser(@PathVariable Long userId, @RequestBody FollowRequest followRequest) {
        followService.followUser(userId, followRequest.getFollowerId());
        return ResponseEntity.ok("Followed successfully!");
    }

}
