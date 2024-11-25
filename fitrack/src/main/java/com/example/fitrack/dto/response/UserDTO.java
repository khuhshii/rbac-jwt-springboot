package com.example.fitrack.dto;

import com.example.fitrack.models.Role;
import lombok.Data;

@Data
public class UserDTO {
    private String email;
    private String password;
}
