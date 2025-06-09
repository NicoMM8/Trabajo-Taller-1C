package com.ubu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.ubu.modelo.UserRole;

/**
 * Configura usuarios en memoria para pruebas.
 * <p>
 * Crea usuarios con roles definidos en {@link UserRole}.
 * </p>
 * 
 * @author Nicolás Muñoz Miguel
 */
@Configuration
public class UserConfiguration {

        /**
         * Define un InMemoryUserDetailsManager con usuarios predefinidos.
         * <ul>
         *   <li><strong>usuario</strong>: rol USER</li>
         *   <li><strong>organizador</strong>: rol ORGANIZADOR</li>
         *   <li><strong>admin</strong>: rol ADMIN</li>
         * </ul>
         * Las contraseñas se codifican usando el {@code PasswordEncoder} proporcionado.
         *
         * @param passwordEncoder Codificador de contraseñas.
         * @return Instancia de {@link InMemoryUserDetailsManager} con los usuarios definidos.
         */
        @Bean
        public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
                UserDetails user = User.builder()
                                .username("usuario")
                                .password(passwordEncoder.encode("claveUsuario"))
                                .roles(UserRole.USER.name())
                                .build();
                                
                UserDetails organizador = User.builder()
                                .username("organizador")
                                .password(passwordEncoder.encode("claveOrganizador"))
                                .roles(UserRole.ORGANIZADOR.name())
                                .build();
                                
                UserDetails admin = User.builder()
                                .username("admin")
                                .password(passwordEncoder.encode("claveAdmin"))
                                .roles(UserRole.ADMIN.name())
                                .build();

                return new InMemoryUserDetailsManager(user, organizador, admin);
        }
}
