package com.ubu.controller;

import com.ubu.modelo.Usuario; // Corrected model path
import com.ubu.service.CustomUserDetailsService;
import com.ubu.service.ServicioUsuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioUsuario servicioUsuario;

    @MockBean
    private CustomUserDetailsService customUserDetailsService; // Required for Spring Security context in @WebMvcTest

    @Test
    @WithMockUser // To satisfy Spring Security context, even if not strictly needed for this GET
    public void testShowRegistroForm() throws Exception {
        mockMvc.perform(get("/registro"))
                .andExpect(status().isOk())
                .andExpect(view().name("registro"));
    }

    @Test
    @WithMockUser // To satisfy Spring Security context
    public void testRegistrarUsuario_Success() throws Exception {
        // Mocking the service layer
        doNothing().when(servicioUsuario).saveUser(any(Usuario.class));

        mockMvc.perform(post("/registro")
                        .param("username", "testuser")
                        .param("email", "test@example.com")
                        .param("password", "password123")
                        .param("confirmPassword", "password123")
                        .param("rol", "USER") // Assuming 'rol' is a required field in Usuario
                        .with(csrf())) // Add CSRF token
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(servicioUsuario, times(1)).saveUser(any(Usuario.class));
    }

    @Test
    @WithMockUser
    public void testRegistrarUsuario_PasswordMismatch() throws Exception {
        mockMvc.perform(post("/registro")
                        .param("username", "testuser")
                        .param("email", "test@example.com")
                        .param("password", "password123")
                        .param("confirmPassword", "wrongpassword")
                        .param("rol", "USER")
                        .with(csrf()))
                .andExpect(status().isOk()) // Should return to the form
                .andExpect(view().name("registro"))
                .andExpect(model().attributeHasFieldErrors("usuario", "confirmPassword"));

        verify(servicioUsuario, never()).saveUser(any(Usuario.class));
    }

    @Test
    @WithMockUser
    public void testRegistrarUsuario_ValidationError_BlankUsername() throws Exception {
        mockMvc.perform(post("/registro")
                        .param("username", "") // Blank username
                        .param("email", "test@example.com")
                        .param("password", "password123")
                        .param("confirmPassword", "password123")
                        .param("rol", "USER")
                        .with(csrf()))
                .andExpect(status().isOk()) // Should return to the form
                .andExpect(view().name("registro"))
                .andExpect(model().attributeHasFieldErrors("usuario", "username")); // Expect error on username field

        verify(servicioUsuario, never()).saveUser(any(Usuario.class));
    }
    
    // Additional test for another validation, e.g., invalid email
    @Test
    @WithMockUser
    public void testRegistrarUsuario_ValidationError_InvalidEmail() throws Exception {
        mockMvc.perform(post("/registro")
                        .param("username", "testuser")
                        .param("email", "notanemail") // Invalid email
                        .param("password", "password123")
                        .param("confirmPassword", "password123")
                        .param("rol", "USER")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("registro"))
                .andExpect(model().attributeHasFieldErrors("usuario", "email"));

        verify(servicioUsuario, never()).saveUser(any(Usuario.class));
    }
}