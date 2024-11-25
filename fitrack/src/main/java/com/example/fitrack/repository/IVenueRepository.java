package com.example.fitrack.repository;

import com.example.fitrack.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVenueRepository extends JpaRepository<Venue, Long> {
}
