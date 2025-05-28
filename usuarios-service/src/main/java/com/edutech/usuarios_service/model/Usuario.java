package com.edutech.usuarios_service.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor 
public class Usuario {

    @Id
    @Column(name = "id_usuario", length = 36, nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "esta_activo", nullable = false)
    private Boolean estaActivo = true;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude  // Evitar recursión en toString
    private List<UsuarioRol> roles = new ArrayList<>();

    /**
     * Genera automáticamente un UUID antes de persistir si no está presente.
     */
    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @Builder
    public Usuario(String nombre, String apellido, String email, String username, String password,
                   LocalDateTime fechaCreacion, Boolean estaActivo) {
        this.id = UUID.randomUUID();
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
