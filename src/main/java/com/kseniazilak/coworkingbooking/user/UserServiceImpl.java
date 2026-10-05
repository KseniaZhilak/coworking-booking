package com.kseniazilak.coworkingbooking.user;

import com.kseniazilak.coworkingbooking.exception.ConflictException;
import com.kseniazilak.coworkingbooking.exception.NotFoundException;
import com.kseniazilak.coworkingbooking.user.dto.UserCreateDto;
import com.kseniazilak.coworkingbooking.user.dto.UserDto;
import com.kseniazilak.coworkingbooking.user.mapper.UserMapper;
import com.kseniazilak.coworkingbooking.user.repository.UserEntity;
import com.kseniazilak.coworkingbooking.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserCreateDto userCreateDto) {
        UserEntity userEntity = userMapper.toUserEntity(userCreateDto);
        if (userRepository.existsByEmail(userEntity.getEmail())) {
            throw new ConflictException("User with email: " + userEntity.getEmail() + " already exists");
        }

        UserEntity saved = userRepository.save(userEntity);
        return userMapper.toUserDto(saved);
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User with id: " + id + " not found")
        );
        return userMapper.toUserDto(userEntity);
    }
}
