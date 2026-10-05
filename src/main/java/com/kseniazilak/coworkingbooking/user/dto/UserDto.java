package com.kseniazilak.coworkingbooking.user.dto;

import com.kseniazilak.coworkingbooking.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private Role role;

}
