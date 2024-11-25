package com.example.fitrack.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "follows")
@Data
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;
}