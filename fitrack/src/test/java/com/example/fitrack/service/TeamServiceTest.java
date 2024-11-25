package com.example.fitrack.service;

import com.example.fitrack.dto.request.TeamRequest;
import com.example.fitrack.models.PlayerStats;
import com.example.fitrack.models.Team;
import com.example.fitrack.models.User;
import com.example.fitrack.repository.IEventRepository;
import com.example.fitrack.repository.IPlayerStatsRepository;
import com.example.fitrack.repository.ITeamRepository;
import com.example.fitrack.repository.IUserRepository;
import com.example.fitrack.services.TeamService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeamServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private ITeamRepository teamRepository;

    @Mock
    private IPlayerStatsRepository playerStatsRepository;

    @Mock
    private IEventRepository eventRepository;

    @InjectMocks
    private TeamService teamService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTeam() {
        TeamRequest teamRequest = new TeamRequest();
        teamRequest.setName("Team Name");
        teamRequest.setMemberIds(List.of(1L, 2L));

        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findAllById(teamRequest.getMemberIds())).thenReturn(List.of(user));
        when(teamRepository.save(any(Team.class))).thenReturn(new Team());

        Team team = teamService.createTeam(teamRequest);

        assertNotNull(team);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findAllById(teamRequest.getMemberIds());
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    public void testGetPlayerStats() {
        PlayerStats playerStats = new PlayerStats();
        playerStats.setId(1L);

        when(playerStatsRepository.findById(1L)).thenReturn(Optional.of(playerStats));

        Optional<PlayerStats> result = teamService.getPlayerStats(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(playerStatsRepository, times(1)).findById(1L);
    }
}
