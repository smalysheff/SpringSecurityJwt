package ru.smal.jwtdemo.controller.dto.request;

import java.util.Set;

public record SignUpRequest(String username, String email, String password, Set<String> roles) {
}