package ru.smal.jwtdemo.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.smal.jwtdemo.controller.dto.request.SignUpRequest;
import ru.smal.jwtdemo.entity.User;
import ru.smal.jwtdemo.mapper.TypeMapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends TypeMapper<User, SignUpRequest> {

    @Mapping(target = "id", ignore = true)
    User map(SignUpRequest request);
}
