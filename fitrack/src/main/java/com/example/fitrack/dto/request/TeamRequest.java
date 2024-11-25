package com.example.fitrack.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class TeamRequest {
    private String name;
    private List<Long> memberIds;
}
