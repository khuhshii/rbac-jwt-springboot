package com.example.fitrack.services;

import com.example.fitrack.dto.request.GroupRequest;
import com.example.fitrack.models.Group;
import com.example.fitrack.models.Team;
import com.example.fitrack.models.User;
import com.example.fitrack.repository.IGroupRepository;
import com.example.fitrack.repository.ITeamRepository;
import com.example.fitrack.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupService {

    @Autowired
    private IGroupRepository groupRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ITeamRepository teamRepository;

    public Group createGroup(GroupRequest request) throws Exception {
        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new Exception("Team not found"));
        Group group = new Group();
        group.setName(request.getName());
        group.setCreatedBy(userRepository.findById(request.getAdminUserId()).orElseThrow());
        group.setTeam(team);
        return groupRepository.save(group);
    }

    public Group addMember(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        group.getMembers().add(user);
        return groupRepository.save(group);
    }

    public Map<String, Object> getGroupsByUser(Long userId) throws Exception {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        List<Group> joinedGroups = groupRepository.findByMembersContaining(user);

        List<Group> allGroups = groupRepository.findAll();
        List<Group> remainingGroups = allGroups.stream()
                .filter(group -> !joinedGroups.contains(group))
                .toList();

        List<Map<String, Object>> joinedGroupsResponse = joinedGroups.stream().map(group -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", group.getId());
            map.put("name", group.getName());
            map.put("createdBy", group.getCreatedBy().getName());
            return map;
        }).toList();

        List<Map<String, Object>> remainingGroupsResponse = remainingGroups.stream().map(group -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", group.getId());
            map.put("name", group.getName());
            map.put("createdBy", group.getCreatedBy().getName());
            return map;
        }).toList();

        return Map.of(
                "joinedGroups", joinedGroupsResponse,
                "remainingGroups", remainingGroupsResponse
        );
    }
}