package com.example.fitrack.repository;
import java.time.LocalDateTime;
import java.util.List;

import com.example.fitrack.models.Event;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventRepository extends JpaRepository<Event, Long> {
}
