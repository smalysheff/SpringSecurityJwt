package ru.smal.jwtdemo.controller.dto.response;

import java.util.List;

public record JwtResponse(String token, String type, Long id, String username, String email, List<String> roles) {
    public JwtResponse(String token, Long id, String username, String email, List<String> roles) {
        this(token, "Bearer", id, username, email, roles);
    }
}
