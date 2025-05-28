package com.edutech.cursos_inscripciones_service.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "evaluacion")
public class Evaluacion {
    @Id
    @Column(name = "id_evaluacion", nullable = false, updatable = false, length = 36)
    private UUID id;

    @NotBlank
    @Size(max = 255)
    @Column(length = 255, nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_modulo", 
                foreignKey = @ForeignKey(name = "fk_evaluacion_modulo"))
    private Modulo modulo;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_curso", nullable = false, 
                foreignKey = @ForeignKey(name = "fk_evaluacion_curso"))
    private Curso curso;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_tipo_evaluacion", nullable = false, 
                foreignKey = @ForeignKey(name = "fk_evaluacion_tipo_evaluacion"))
    private TipoEvaluacion tipoEvaluacion;

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
     * @param modulo modulo al que pertenece la evaluación
     * @param curso curso al que pertenece la evaluacion
     * @param tipoEvaluacion el tipo de evaluacion
     */
    public Evaluacion(String nombre, String descripcion, Modulo modulo, Curso curso, TipoEvaluacion tipoEvaluacion) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.modulo = modulo;
        this.curso = curso;
        this.tipoEvaluacion = tipoEvaluacion;
    }

    

}
