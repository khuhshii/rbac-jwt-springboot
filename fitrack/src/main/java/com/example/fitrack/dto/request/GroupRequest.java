package com.example.fitrack.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
public class GroupRequest {

    @NotBlank(message = "Group name is required.")
    private String name;

    @NotBlank(message = "Group description is required.")
    private String description;

    @NotNull(message = "Please provide the team ID this group is associated with.")
    private Long teamId;

    @NotNull(message = "Group must have at least one admin.")
    private Long adminUserId;
}