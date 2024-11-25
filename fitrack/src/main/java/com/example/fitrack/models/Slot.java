package com.example.fitrack.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;


@Entity
@Data
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private LocalTime time;

    private boolean availability;
    private int registered_users;
    private int max_users;

}
