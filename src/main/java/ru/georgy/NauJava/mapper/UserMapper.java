package ru.georgy.NauJava.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.georgy.NauJava.model.User;
import ru.georgy.NauJava.service.user.UserInput;
import ru.georgy.NauJava.service.user.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserInput input);
}
