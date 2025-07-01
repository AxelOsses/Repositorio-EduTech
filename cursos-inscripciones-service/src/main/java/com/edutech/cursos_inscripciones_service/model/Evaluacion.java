package com.edutech.cursos_inscripciones_service.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "evaluacion")
public class Evaluacion {

    /**
     * Identificador único de la evaluación
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental en MySQL
    @Column(name = "id_evaluacion", updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(length = 255, nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_modulo", 
                foreignKey = @ForeignKey(name = "fk_evaluacion_modulo"))
    @JsonBackReference
    private Modulo modulo;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_curso", nullable = false, 
                foreignKey = @ForeignKey(name = "fk_evaluacion_curso"))
    @JsonBackReference
    private Curso curso;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_tipo_evaluacion", nullable = false, 
                foreignKey = @ForeignKey(name = "fk_evaluacion_tipo_evaluacion"))
    private TipoEvaluacion tipoEvaluacion;


    /**
     * Constructor para crear una asignación con generación automática de ID.
     * @param nombre nombre del tipo de evaluacion
     * @param descripcion descripcion del tipo de evaluacion
     * @param modulo modulo al que pertenece la evaluación
     * @param curso curso al que pertenece la evaluacion
     * @param tipoEvaluacion el tipo de evaluacion
     */
    public Evaluacion(String nombre, String descripcion, Modulo modulo, Curso curso, TipoEvaluacion tipoEvaluacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.modulo = modulo;
        this.curso = curso;
        this.tipoEvaluacion = tipoEvaluacion;
    }

    

}
