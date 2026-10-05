package com.kseniazilak.coworkingbooking.user;

import com.kseniazilak.coworkingbooking.user.dto.UserCreateDto;
import com.kseniazilak.coworkingbooking.user.dto.UserDto;

public interface UserService {

    UserDto createUser(UserCreateDto userCreateDto);

    UserDto getUserById(Long id);
}
