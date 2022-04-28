package com.axiomq.starwars.web.dtos.user;

import com.axiomq.starwars.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User fromRequest(UserRequest userRequest);
    UserResponse toResponse(User user);
}
