package com.ubu.modelo;

/**
 * Enum que define los roles posibles para los usuarios de la aplicación.
 * Los roles son utilizados para gestionar los permisos y accesos de los usuarios dentro del sistema.
 * Los roles definidos son:
 *   user: Usuario estándar.</li>
 *   organizador: Usuario con permiso para crear y gestionar eventos.</li>
 *   admin: Usuario administrador con permisos avanzados.</li>
 * Estos valores se utilizan, por ejemplo, en la configuración de Spring Security para restringir el acceso a ciertos endpoints.
 * 
 * @author Nicolás Muñoz Miguel
 */
public enum UserRole {
    USER,
    ORGANIZADOR,
    ADMIN
}

