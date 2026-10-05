package com.kseniazilak.coworkingbooking.user.dto;

import com.kseniazilak.coworkingbooking.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

    @Size(max = 100, message = "Имя не длиннее 100 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым!")
    private String name;

    @NotBlank(message = "Электронная почта пользователя не может быть пустой!")
    @Email
    @Size(max = 255, message = "Почта не длиннее 255 символов")
    private String email;

    @NotNull(message = "Роль обязательна для заполнения!")
    private Role role;

}
