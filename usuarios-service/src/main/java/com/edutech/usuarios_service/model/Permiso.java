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
    @Column(name = "id_permiso", nullable = false, updatable = false)
    private UUID id = UUID.randomUUID(); 

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    // Constructor adicional
    public Permiso(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
