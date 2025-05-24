package com.edutech.usuarios_service.model;

import java.time.LocalDateTime;
import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {

    @Id
    @Column(name = "id_rol", nullable = false, updatable = false)
    private UUID id = UUID.randomUUID(); 

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(nullable = false)
    private boolean estaActivo = true;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude  // Evitar recursión en toString
    private List<UsuarioRol> usuarios = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "rol_permiso",
        joinColumns = @JoinColumn(name = "rol_id"),
        inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    @ToString.Exclude
    private Set<Permiso> permisos = new HashSet<>();

    // Constructor personalizado adicional
    public Rol(String nombre, String descripcion, LocalDateTime fechaCreacion, boolean estaActivo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = (fechaCreacion != null) ? fechaCreacion : LocalDateTime.now();
        this.estaActivo = estaActivo;
    }
}
