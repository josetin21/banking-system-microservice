package com.josetin.userservice.dto.request;

import com.josetin.userservice.entity.Role;

public record RegisterRequest(
        String username,
        String password,
        Role role
) {}
