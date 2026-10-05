package com.kseniazilak.coworkingbooking.user.mapper;

import com.kseniazilak.coworkingbooking.user.dto.UserCreateDto;
import com.kseniazilak.coworkingbooking.user.dto.UserDto;
import com.kseniazilak.coworkingbooking.user.repository.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity toUserEntity(UserCreateDto userCreateDto);

    UserDto toUserDto(UserEntity userEntity);
}
