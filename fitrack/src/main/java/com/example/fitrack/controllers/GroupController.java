package com.example.fitrack.controllers;

import com.example.fitrack.dto.request.JoinRequest;
import com.example.fitrack.models.Group;
import com.example.fitrack.services.GroupService;
import com.example.fitrack.dto.request.GroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/groups")
    public ResponseEntity<Group> createGroup(@RequestBody GroupRequest request) throws Exception {
        Group group = groupService.createGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }

    @PostMapping("/groups/{groupId}/join")
        public ResponseEntity<Group> joinGroup(@PathVariable Long groupId, @RequestBody JoinRequest joinRequest) {
        Group group = groupService.addMember(groupId, joinRequest.getUserId());
        return ResponseEntity.ok(group);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getGroupsByUser(@PathVariable Long userId) throws Exception {
        Map<String, Object> response = groupService.getGroupsByUser(userId);
        return ResponseEntity.ok(response);
    }




}
