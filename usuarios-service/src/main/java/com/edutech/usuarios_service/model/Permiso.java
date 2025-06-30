package com.edutech.usuarios_service.model;

import jakarta.persistence.*;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "permiso")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Modelo de Permiso")
public class Permiso extends RepresentationModel<Permiso> {

    /**
     * Identificador único de permiso.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental en MySQL
    @Column(name = "id_permiso", updatable = false, nullable = false)
    @Schema(description = "ID único del permiso", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del permiso", example = "USUARIO_CREAR", required = true)
    private String nombre;

    @Column
    @Schema(description = "Descripción del permiso", example = "Permite crear nuevos usuarios")
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
