package ru.smal.jwtdemo.service;

import ru.smal.jwtdemo.controller.dto.request.SignUpRequest;
import ru.smal.jwtdemo.controller.dto.response.MessageResponse;
import ru.smal.jwtdemo.entity.Role;
import ru.smal.jwtdemo.entity.User;

import java.util.List;

public interface UserService {
    MessageResponse saveUser(SignUpRequest user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String role);
    User findByName(String username);
    List<User> findAllUsers();
}
