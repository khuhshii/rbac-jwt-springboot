package com.example.fitrack.dto;

import com.example.fitrack.models.Category;
import lombok.Data;

@Data
public class EventDTO {
    private String name;
    private int teamSize;
    private Category category;
    private Long createdById;
    private Long venueId;
}
