package com.ubu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        // Rutas públicas
                        .antMatchers("/", "/login", "/css/**", "/js/**", "/images/**", "/registro").permitAll()

                        // Gestión de eventos: accesible para organizador y admin
                        .antMatchers("/eventos/**").hasAnyRole("ORGANIZADOR", "ADMIN")

                        // Gestión de usuarios en general (lista completa, eliminación, asignaciones, etc.)
                        .antMatchers("/usuarios/todos/**").hasRole("ADMIN")

                        // Rutas para el usuario autenticado: modificar su perfil, etc.
                        .antMatchers("/usuarios/miPerfil/**").authenticated()

                        // El resto: acceso autenticado
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }
}





