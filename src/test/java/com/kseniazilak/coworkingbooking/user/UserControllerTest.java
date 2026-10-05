package com.kseniazilak.coworkingbooking.user;

import com.kseniazilak.coworkingbooking.exception.ConflictException;
import com.kseniazilak.coworkingbooking.exception.NotFoundException;
import com.kseniazilak.coworkingbooking.user.dto.UserDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    private static final String VALID_JSON = """
                {"name": "Ксения", "email": "test@example.com", "role": "USER"}
            """;

    @Test
    public void create_user_201() throws Exception {
        when(userService.createUser(any()))
                .thenReturn(new UserDto(1L, "Тестовый", "k@example.com", Role.USER));

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void create_user_blankName_400() throws Exception {
        String INVALID_JSON = """
                    {"name": "", "email": "k@example.com", "role": "USER"}
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(INVALID_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.containsString("name")));

        verify(userService, never()).createUser(any());
    }

    @Test
    void create_user_unknownRole_400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "Ксения", "email": "test@example.com", "role": "MANAGER"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Некорректное тело запроса"));

        verify(userService, never()).createUser(any());
    }

    @Test
    public void create_user_duplicateEmail_409() throws Exception {
        when(userService.createUser(any()))
                .thenThrow(new ConflictException("User with email: test@example.com already exists"));

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message")
                        .value("User with email: test@example.com already exists"));
    }

    @Test
    public void get_user_by_id() throws Exception {
        when(userService.getUserById(1L))
                .thenReturn(new UserDto(1L, "Тестовый", "k@example.com", Role.USER));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void get_user_by_id_not_found_404() throws Exception {
        when(userService.getUserById(1L)).thenThrow(new NotFoundException("User 1 not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User 1 not found"));
    }

    @Test
    void get_user_by_invalid_id_400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }

    @Test
    public void unknown_endpoint_404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/unknown"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("NOT_FOUND"));
    }
}