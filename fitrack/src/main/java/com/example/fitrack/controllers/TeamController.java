package com.example.fitrack.controllers;
import com.example.fitrack.dto.request.TeamRequest;
import com.example.fitrack.models.PlayerStats;
import com.example.fitrack.models.Team;
import com.example.fitrack.repository.IPlayerStatsRepository;
import com.example.fitrack.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TeamController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private IPlayerStatsRepository playerStatsRepository;

    @PostMapping("/createTeams")
    public ResponseEntity<Team> createTeam(@RequestBody TeamRequest request) {
        Team team = teamService.createTeam(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(team);
    }

    @GetMapping("/teams/{teamId}/players/stats")
    public ResponseEntity<?> getPlayerStats(@PathVariable Long teamId) {
        return ResponseEntity.ok(playerStatsRepository.findById(teamId).toString());
    }
}
