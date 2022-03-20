package ru.smal.jwtdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.smal.jwtdemo.config.jwt.JwtUtils;
import ru.smal.jwtdemo.controller.dto.request.LoginRequest;
import ru.smal.jwtdemo.controller.dto.request.SignUpRequest;
import ru.smal.jwtdemo.controller.dto.response.JwtResponse;
import ru.smal.jwtdemo.controller.dto.response.MessageResponse;
import ru.smal.jwtdemo.entity.Role;
import ru.smal.jwtdemo.entity.User;
import ru.smal.jwtdemo.enums.RoleEnum;
import ru.smal.jwtdemo.repository.RoleRepository;
import ru.smal.jwtdemo.repository.UserRepository;
import ru.smal.jwtdemo.service.UserDetailsImpl;
import ru.smal.jwtdemo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.id(),
                userDetails.username(),
                userDetails.email(),
                roles));
    }

    @PostMapping("signup")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody SignUpRequest signupRequest) {
        MessageResponse messageResponse = userService.saveUser(signupRequest);
        return ResponseEntity.ok(messageResponse);
    }
}
