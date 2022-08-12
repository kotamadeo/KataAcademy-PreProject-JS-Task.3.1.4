package com.gmail.at.kotamadeo;

import com.gmail.at.kotamadeo.configuration.AuthenticationSuccessUserHandler;
import com.gmail.at.kotamadeo.controller.AdminRestController;
import com.gmail.at.kotamadeo.models.Role;
import com.gmail.at.kotamadeo.services.RoleService;
import com.gmail.at.kotamadeo.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminRestController.class)
class MvcTest {
    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;

    @MockBean
    private AuthenticationSuccessUserHandler authenticationSuccessUserHandler;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void testFindAllRoles() throws Exception {
        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");
        List<Role> roles = Arrays.asList(admin, user);
        Mockito.when(roleService.findAll()).thenReturn(new HashSet<>(roles));

    }

    @Test
    void shouldReturnLoginPage() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Пожалуйста, залогиньтесь!")));
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN", password = "test")
    void shouldReturnAdminPageAccess() throws Exception {
        this.mockMvc
                .perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Панель администратора")));
    }

    @Test
    @WithMockUser(username = "test", roles = "USER", password = "test")
    void shouldReturnAdminPageForbidden() throws Exception {
        this.mockMvc
                .perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnAdminUnauthorized() throws Exception {
        this.mockMvc
                .perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", roles = {"ADMIN", "USER"}, password = "test")
    void shouldReturnUserPageAccess() throws Exception {
        this.mockMvc
                .perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Панель пользователя")));
    }

    @Test
    void shouldReturnUserPageUnauthorized() throws Exception {
        this.mockMvc
                .perform(get("/user"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
