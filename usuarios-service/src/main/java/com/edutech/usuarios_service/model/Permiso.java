package com.edutech.usuarios_service.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permiso")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permiso {

    @Id
    @Column(name = "id_permiso", nullable = false, updatable = false, length = 36)
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    /**
     * Genera automáticamente un UUID antes de persistir si no está presente.
     */
    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    /**
     * Constructor para crear una asignación con generación automática de ID.
     * @param nombre nombre del permiso
     * @param descripcion descripcion del permiso
     */
    public Permiso(String nombre, String descripcion) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
