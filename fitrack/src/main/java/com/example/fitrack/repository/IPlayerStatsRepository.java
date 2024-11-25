package com.example.fitrack.repository;

import com.example.fitrack.models.PlayerStats;
import com.example.fitrack.models.Team;
import com.example.fitrack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlayerStatsRepository extends JpaRepository<PlayerStats, Long> {
}
