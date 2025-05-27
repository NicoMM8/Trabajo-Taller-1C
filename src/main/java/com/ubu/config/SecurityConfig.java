package com.ubu.config;

import com.ubu.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Added import
import org.springframework.security.web.SecurityFilterChain;
import com.ubu.modelo.UserRole; // Added import

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { // Added PasswordEncoder bean
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        // Rutas públicas
                        .antMatchers("/", "/login", "/css/**", "/js/**", "/images/**", "/registro").permitAll()
                        // Gestión de eventos: el GET es público
                        .antMatchers(HttpMethod.GET, "/eventos/**").permitAll()
                        // Rutas para la gestión de eventos (crear, editar, eliminar): solo accesible para ORGANIZADOR y ADMIN
                        .antMatchers("/eventos/nuevo", "/eventos/guardar", "/eventos/editar/**", "/eventos/eliminar/**")
                        .hasAnyRole(UserRole.ROLE_ORGANIZADOR.name().substring("ROLE_".length()), UserRole.ROLE_ADMIN.name().substring("ROLE_".length())) // Updated roles
                        // Gestión de usuarios en general (lista completa, eliminación, asignaciones, etc.)
                        .antMatchers("/usuarios/todos/**").hasRole(UserRole.ROLE_ADMIN.name().substring("ROLE_".length())) // Updated role
                        // Rutas para el usuario autenticado: modificar su perfil, etc.
                        .antMatchers("/usuarios/miPerfil/**").authenticated()
                        // El resto: acceso autenticado
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .userDetailsService(customUserDetailsService); // Se utiliza el servicio personalizado

        return http.build();
    }
}





