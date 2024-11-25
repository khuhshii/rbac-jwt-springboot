package com.example.fitrack.service;

import com.example.fitrack.dto.request.GroupRequest;
import com.example.fitrack.models.Group;
import com.example.fitrack.models.Team;
import com.example.fitrack.models.User;
import com.example.fitrack.repository.IGroupRepository;
import com.example.fitrack.repository.ITeamRepository;
import com.example.fitrack.repository.IUserRepository;
import com.example.fitrack.services.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GroupServiceTest {

    @Mock
    private IGroupRepository groupRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private ITeamRepository teamRepository;

    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateGroup() throws Exception {
        GroupRequest request = new GroupRequest();
        request.setName("Group Name");
        request.setTeamId(1L);
        request.setAdminUserId(1L);

        Team team = new Team();
        team.setId(1L);

        User user = new User();
        user.setId(1L);

        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(groupRepository.save(any(Group.class))).thenReturn(new Group());

        Group group = groupService.createGroup(request);

        assertNotNull(group);
        verify(teamRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findById(anyLong());
        verify(groupRepository, times(1)).save(any(Group.class));
    }

    @Test
    public void testGetGroupsByUser() throws Exception {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Group group1 = new Group();
        group1.setId(1L);
        group1.setName("Group 1");
        group1.setCreatedBy(user);

        Group group2 = new Group();
        group2.setId(2L);
        group2.setName("Group 2");
        group2.setCreatedBy(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(groupRepository.findByMembersContaining(user)).thenReturn(List.of(group1));
        when(groupRepository.findAll()).thenReturn(List.of(group1, group2));

        Map<String, Object> groups = groupService.getGroupsByUser(userId);

        assertNotNull(groups);
        assertTrue(groups.containsKey("joinedGroups"));
        assertTrue(groups.containsKey("remainingGroups"));
        verify(userRepository, times(1)).findById(userId);
        verify(groupRepository, times(1)).findByMembersContaining(user);
        verify(groupRepository, times(1)).findAll();
    }
}
