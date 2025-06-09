package com.ubu.config;

import com.ubu.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad para la aplicación.
 * <p>
 * Esta configuración define las reglas de acceso a los endpoints,
 * estableciendo rutas públicas, rutas protegidas según roles y las páginas para login y logout.
 * </p>
 * 
 * @author Nicolás Muñoz Miguel
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Constructor para inyectar la dependencia del servicio personalizado de detalles de usuario.
     * 
     * @param customUserDetailsService Servicio personalizado para la carga de usuarios.
     */
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Declara y configura el bean PasswordEncoder que se usará para la encriptación de contraseñas.
     * 
     * @return instancia de BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura la cadena de filtros de seguridad (SecurityFilterChain) para proteger los endpoints de la aplicación.
     * <p>
     * Se definen:
     * <ul>
     *   <li>Rutas públicas: '/', '/login', recursos estáticos y '/registro'.</li>
     *   <li>Rutas GET para "/eventos/**" son públicas.</li>
     *   <li>Rutas para gestionar eventos (crear, editar, eliminar) accesibles solo para usuarios con roles ORGANIZADOR y ADMIN.</li>
     *   <li>Rutas para la gestión de usuarios (lista y asignación) accesibles solo para ADMIN.</li>
     *   <li>El perfil de usuario requiere autenticación.</li>
     *   <li>El resto de endpoints requieren autenticación.</li>
     * </ul>
     * Además configura la página de login personalizada y el logout.
     * </p>
     * 
     * @param http instancia de HttpSecurity para configurar la seguridad.
     * @return SecurityFilterChain configurada.
     * @throws Exception en caso de error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorize -> authorize
                // Rutas públicas sin autenticación
                .antMatchers("/", "/login", "/css/**", "/js/**", "/images/**", "/registro").permitAll()
                // Permite acceso a eventos mediante solicitudes GET
                .antMatchers(HttpMethod.GET, "/eventos/**").permitAll()
                // Solo usuarios autenticados pueden editar su perfil
                .antMatchers("/usuarios/editar/**").hasAnyRole("USER", "ADMIN")
                // Solo administradores pueden eliminar usuarios
                .antMatchers("/usuarios/eliminar/**").hasRole("ADMIN")
                // Solo organizadores y administradores pueden editar/eliminar eventos
                .antMatchers("/eventos/editar/**", "/eventos/eliminar/**").hasAnyRole("ORGANIZADOR", "ADMIN")
                // Solo organizadores y administradores pueden crear eventos
                .antMatchers("/eventos/nuevo", "/eventos/guardar").hasAnyRole("ORGANIZADOR", "ADMIN")
                // El resto requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")                   // Define la página personalizada de login
                .defaultSuccessUrl("/", true)            // Redirige a la página principal tras login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")                    // Ruta para realizar el logout
                .logoutSuccessUrl("/?logout")       // Redirige a la página de inicio con mensaje de logout
                .permitAll()
            )
            .userDetailsService(customUserDetailsService); // Servicio personalizado para la carga de usuarios

        return http.build();
    }
}
