package com.example.fitrack.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchEventsRequest {
    private String name;
    private String location;
    private LocalDateTime startTime;
}
