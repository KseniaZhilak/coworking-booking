package com.kseniazilak.coworkingbooking.user;

import com.kseniazilak.coworkingbooking.exception.ConflictException;
import com.kseniazilak.coworkingbooking.exception.NotFoundException;
import com.kseniazilak.coworkingbooking.user.dto.UserCreateDto;
import com.kseniazilak.coworkingbooking.user.dto.UserDto;
import com.kseniazilak.coworkingbooking.user.mapper.UserMapper;
import com.kseniazilak.coworkingbooking.user.repository.UserEntity;
import com.kseniazilak.coworkingbooking.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void create_user() {
        UserEntity entity = new UserEntity(1L, "Тестовый", "k@email.com", Role.ADMIN);
        UserCreateDto userCreateDto = new UserCreateDto("Тестовый", "k@email.com", Role.USER);
        UserDto dto = new UserDto(1L, "Тестовый", "k@email.com", Role.ADMIN);

        when(userMapper.toUserEntity(userCreateDto)).thenReturn(entity);
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toUserDto(entity)).thenReturn(dto);

        UserDto result = userService.createUser(userCreateDto);

        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).save(entity);

    }

    @Test
    void create_duplicateEmail_user() {
        UserEntity entity = new UserEntity(1L, "Тестовый", "k@email.com", Role.ADMIN);
        UserCreateDto userCreateDto = new UserCreateDto("Тестовый", "k@email.com", Role.USER);

        when(userMapper.toUserEntity(userCreateDto)).thenReturn(entity);
        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(ConflictException.class, () -> userService.createUser(userCreateDto));
        verify(userRepository, never()).save(any());

    }

    @Test
    void get_user_by_id() {
        UserEntity entity = new UserEntity(1L, "Тестовый", "k@email.com", Role.ADMIN);
        UserDto dto = new UserDto(1L, "Тестовый", "k@email.com", Role.ADMIN);

        when(userRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(userMapper.toUserDto(entity)).thenReturn(dto);

        assertEquals(1L, userService.getUserById(1L).getId());

    }

    @Test
    void getById_throwsNotFound_whenUserMissing() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(99L));
    }
}