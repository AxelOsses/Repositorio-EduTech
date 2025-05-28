package com.edutech.cursos_inscripciones_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    /**
     * Identificador único del tipoEvaluacion.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental en MySQL
    @Column(name = "id_tipo_evaluacion", updatable = false, nullable = false)
    private Long id;

    @Size(max = 255)
    @NotBlank
    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    /**
     * Constructor para crear una asignación con generación automática de ID.
     * @param nombre nombre del tipo de evaluacion
     * @param descripcion descripcion del tipo de evaluacion
     */
    public TipoEvaluacion(@Size(max = 255) @NotBlank String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    

}
