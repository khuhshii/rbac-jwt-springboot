package com.example.fitrack.repository;

import com.example.fitrack.models.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFollowRepository extends JpaRepository<Follow, Long> {
}
