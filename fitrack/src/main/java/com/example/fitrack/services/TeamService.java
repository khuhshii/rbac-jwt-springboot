package com.example.fitrack.services;

import com.example.fitrack.dto.request.TeamRequest;
import com.example.fitrack.models.Event;
import com.example.fitrack.models.PlayerStats;
import com.example.fitrack.models.Team;
import com.example.fitrack.models.User;
import com.example.fitrack.repository.IEventRepository;
import com.example.fitrack.repository.IPlayerStatsRepository;
import com.example.fitrack.repository.ITeamRepository;
import com.example.fitrack.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ITeamRepository teamRepository;
    @Autowired
    private IPlayerStatsRepository playerStatsRepository;

    @Autowired
    private IEventRepository eventRepository;

    public Team createTeam(TeamRequest teamRequest) {
        Team team = new Team();
        team.setName(teamRequest.getName());
        User creator = userRepository.findById(teamRequest.getMemberIds().get(0))
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + teamRequest.getMemberIds().get(0)));

        team.setCreatedBy(creator);
        List<User> members = userRepository.findAllById(teamRequest.getMemberIds()).stream()
                .collect(Collectors.toList());
                
        members.add(creator);
        team.setMembers(members);

        return teamRepository.save(team);
    }

    public Optional<PlayerStats> getPlayerStats(Long teamId) {
        return playerStatsRepository.findById(teamId);
    }
}
