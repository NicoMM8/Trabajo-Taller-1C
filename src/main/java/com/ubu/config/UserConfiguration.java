package com.ubu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("usuario")
                .password(passwordEncoder.encode("claveUsuario"))
                .roles("USER") // Usuario normal
                .build();
        UserDetails organizador = User.builder()
                .username("organizador")
                .password(passwordEncoder.encode("claveOrganizador"))
                .roles("ORGANIZADOR") // Puede gestionar eventos
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("claveAdmin"))
                .roles("ADMIN") // Puede gestionar tanto eventos como usuarios
                .build();
        return new InMemoryUserDetailsManager(user, organizador, admin);
    }
}


