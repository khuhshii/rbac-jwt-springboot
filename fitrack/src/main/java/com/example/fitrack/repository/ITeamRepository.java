package com.example.fitrack.repository;

import com.example.fitrack.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITeamRepository extends JpaRepository<Team, Long> {
}
