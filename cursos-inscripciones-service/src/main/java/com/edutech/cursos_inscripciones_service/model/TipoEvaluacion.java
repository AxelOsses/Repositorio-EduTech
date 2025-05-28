package com.edutech.cursos_inscripciones_service.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tipo_evaluacion")
public class TipoEvaluacion {
    @Id
    @Column(name = "id_tipo_evaluacion", nullable = false, updatable = false, length = 36)
    private UUID id;

    @Size(max = 255)
    @NotBlank
    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(columnDefinition = "TEXT")
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
     * @param nombre nombre del tipo de evaluacion
     * @param descripcion descripcion del tipo de evaluacion
     */
    public TipoEvaluacion(@Size(max = 255) @NotBlank String nombre, String descripcion) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    

}
