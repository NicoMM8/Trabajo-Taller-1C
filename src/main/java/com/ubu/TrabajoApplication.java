package com.ubu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.ubu.modelo.UserRole;
import com.ubu.modelo.Usuario;
import com.ubu.repository.RepositorioUsuario;

/**
 * Clase principal para iniciar la aplicación Spring Boot.
 * 
 * Contiene el método main y la carga inicial de usuarios de ejemplo.
 * 
 * @author Nicolás Muñoz Miguel
 */
@SpringBootApplication
public class TrabajoApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(TrabajoApplication.class, args);
    }

    /**
     * Bean que carga usuarios iniciales en la base de datos si está vacía.
     *
     * @param repositorioUsuario Repositorio de usuarios.
     * @param passwordEncoder    Codificador de contraseñas.
     * @return CommandLineRunner que inserta usuarios de ejemplo.
     */
    @Bean
    public CommandLineRunner loadInitialUsers(RepositorioUsuario repositorioUsuario, PasswordEncoder passwordEncoder) {
        return args -> {
            // Solo inserta usuarios si la tabla está vacía
            if (repositorioUsuario.count() == 0) {
                Usuario user = new Usuario("user1", passwordEncoder.encode("password"), "user1@example.com", UserRole.USER);
                Usuario org = new Usuario("organizador1", passwordEncoder.encode("password"), "org1@example.com", UserRole.ORGANIZADOR);
                Usuario admin = new Usuario("admin1", passwordEncoder.encode("password"), "admin1@example.com", UserRole.ADMIN);

                repositorioUsuario.save(user);
                repositorioUsuario.save(org);
                repositorioUsuario.save(admin);
            }
        };
    }
}
