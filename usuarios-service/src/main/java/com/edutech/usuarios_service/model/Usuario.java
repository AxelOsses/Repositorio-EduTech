package com.edutech.usuarios_service.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor 
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Modelo de Usuario")
public class Usuario extends RepresentationModel<Usuario> {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental en MySQL
    @Column(name = "id_usuario", updatable = false, nullable = false)
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del usuario", example = "Juan", required = true)
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Apellido del usuario", example = "Pérez", required = true)
    private String apellido;

    @Column(nullable = false, unique = true)
    @Schema(description = "Email único del usuario", example = "juan.perez@edutech.com", required = true)
    private String email;

    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre de usuario único", example = "jperez", required = true)
    private String username;

    @Column(nullable = false)
    @Schema(description = "Contraseña del usuario", example = "password123", required = true)
    @JsonIgnore  // No incluir contraseña en respuestas JSON por seguridad
    private String password;

    @Column(name = "fecha_creacion", nullable = false)
    @Schema(description = "Fecha de creación del usuario", example = "2024-01-15T10:30:00")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "esta_activo", nullable = false)
    @Schema(description = "Indica si el usuario está activo", example = "true")
    private Boolean estaActivo = true;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude  // Evitar recursión en toString
    @JsonManagedReference  // Evitar recursión infinita en JSON
    @JsonIgnore // Oculta la lista de roles en la respuesta JSON
    @Schema(hidden = true) // Oculta en Swagger
    private List<UsuarioRol> roles = new ArrayList<>();


    @Builder
    public Usuario(String nombre, String apellido, String email, String username, String password,
                   LocalDateTime fechaCreacion, Boolean estaActivo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.username = username;
        this.password = password;
        this.fechaCreacion = (fechaCreacion != null) ? fechaCreacion : LocalDateTime.now();
        this.estaActivo = (estaActivo != null) ? estaActivo : true;
    }

    // Métodos específicos
    public List<UsuarioRol> getRoles() {
        return new ArrayList<>(roles);  // Encapsulamiento
    }

    public List<Rol> getRolesDirectos() {
        return roles.stream()
                    .map(UsuarioRol::getRol)
                    .collect(Collectors.toList());
    }

    public boolean agregarRol(Rol rol) {
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser null");
        }
        boolean yaExiste = roles.stream()
                                .anyMatch(ur -> ur.getRol().equals(rol));
        if (!yaExiste) {
            UsuarioRol ur = new UsuarioRol(this, rol);
            roles.add(ur);
            return true;
        }
        return false;
    }

    public boolean eliminarRol(Rol rol) {
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser null");
        }
        return roles.removeIf(ur -> ur.getRol().equals(rol));
    }
}
