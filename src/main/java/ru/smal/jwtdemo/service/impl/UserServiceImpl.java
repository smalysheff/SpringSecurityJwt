package ru.smal.jwtdemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.smal.jwtdemo.controller.dto.request.SignUpRequest;
import ru.smal.jwtdemo.controller.dto.response.MessageResponse;
import ru.smal.jwtdemo.entity.Role;
import ru.smal.jwtdemo.entity.User;
import ru.smal.jwtdemo.enums.RoleEnum;
import ru.smal.jwtdemo.repository.RoleRepository;
import ru.smal.jwtdemo.repository.UserRepository;
import ru.smal.jwtdemo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MessageResponse saveUser(SignUpRequest signupRequest) {
        if (userRepository.existsUserByUsername(signupRequest.username())) {
            return new MessageResponse("Error: Username is exist");
        }
        if (userRepository.existsUserByEmail(signupRequest.email())) {
            return new MessageResponse("Error: Email is exist");
        }

        User user = new User(signupRequest.username(), signupRequest.email(), passwordEncoder.encode(signupRequest.password()));
        Set<String> reqRoles = signupRequest.roles();
        Set<Role> roles = new HashSet<>();

        if (reqRoles == null) {
            Role role = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(role);
        } else {
            reqRoles.forEach(r -> {
                switch (r) {
                    case "admin" -> {
                        Role roleAdmin = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
                        roles.add(roleAdmin);
                    }
                    case "mod" -> {
                        Role roleMod = roleRepository.findByName(RoleEnum.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error, Role MODERATOR is not found"));
                        roles.add(roleMod);
                    }
                    default -> {
                        Role roleUser = roleRepository.findByName(RoleEnum.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                        roles.add(roleUser);
                    }
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return new MessageResponse("CREATED" + user);
    }

    @Override
    public Role saveRole(Role role) {
        return null;
    }

    @Override
    public void addRoleToUser(String username, String role) {

    }

    @Override
    public User findByName(String username) {
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        return null;
    }
}
