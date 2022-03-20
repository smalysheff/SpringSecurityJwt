package ru.smal.jwtdemo.mapper.user;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.smal.jwtdemo.controller.dto.request.SignUpRequest;
import ru.smal.jwtdemo.entity.User;
import ru.smal.jwtdemo.mapper.TypeMapper;

@Mapper(componentModel = "spring", uses = PasswordEncoder.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper extends TypeMapper<User, SignUpRequest> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "username", expression = "java(request.username())")
    @Mapping(target = "email", expression = "java(request.email())")
    User map(SignUpRequest request);
}
