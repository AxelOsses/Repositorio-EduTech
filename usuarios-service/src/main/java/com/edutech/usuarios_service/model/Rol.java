package com.edutech.usuarios_service.model;

import java.time.LocalDateTime;
import java.util.*;

import jakarta.persistence.*;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Modelo de Rol")
public class Rol extends RepresentationModel<Rol> {

    /**
     * Identificador único del CursoCategoria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental en MySQL
    @Column(name = "id_curso", updatable = false, nullable = false)
    @Schema(description = "ID único del rol", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del rol", example = "ADMINISTRADOR", required = true)
    private String nombre;

    @Column
    @Schema(description = "Descripción del rol", example = "Rol con acceso completo al sistema")
    private String descripcion;

    @Column(name = "fecha_creacion", nullable = false)
    @Schema(description = "Fecha de creación del rol", example = "2024-01-15T10:30:00")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(nullable = false)
    @Schema(description = "Indica si el rol está activo", example = "true")
    private boolean estaActivo = true;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude  // Evitar recursión en toString
    @JsonManagedReference  // Evitar recursión infinita en JSON
    @Schema(description = "Usuarios que tienen este rol")
    private List<UsuarioRol> usuarios = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "rol_permiso",
        joinColumns = @JoinColumn(name = "rol_id"),
        inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    @ToString.Exclude
    @Schema(description = "Permisos asignados al rol")
    private Set<Permiso> permisos = new HashSet<>();


    /**
     * Constructor para crear una asignación con generación automática de ID.
     * @param nombre nombre del rol
     * @param descripcion descipcion del rol
     */
    public Rol(String nombre, String descripcion, LocalDateTime fechaCreacion, boolean estaActivo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = (fechaCreacion != null) ? fechaCreacion : LocalDateTime.now();
        this.estaActivo = estaActivo;
    }
}
