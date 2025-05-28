package com.edutech.usuarios_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permiso")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permiso {

    /**
     * Identificador único de permiso.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental en MySQL
    @Column(name = "id_permiso", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    /**
     * Constructor para crear una asignación con generación automática de ID.
     * @param nombre nombre del permiso
     * @param descripcion descripcion del permiso
     */
    public Permiso(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
