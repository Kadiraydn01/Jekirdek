package com.jekirdek.project.Dto;

import com.jekirdek.project.Entity.Role;
import lombok.Data;

@Data
public class UserDto {
    private String username;
    private Role role;
}
