package com.edutech.cursos_inscripciones_service.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Entidad que representa la asignación de un instructor a un curso,
 * incluyendo la fecha en que se otorgó esta asignación.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "instructor_curso")
public class InstructorCurso {

    /**
     * Identificador único del registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental en MySQL
    @Column(name = "id_instructor_curso", updatable = false, nullable = false)
    private Long id;

    /**
     * Identificador del instructor, proveniente del microservicio usuarios-service.
     */
    @NotNull
    @Column(name = "id_usuario", nullable = false)
    private Long instructorId;

    /**
     * Curso asociado (relación local hacia Curso.id).
     */
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_curso", nullable = false,
                foreignKey = @ForeignKey(name = "fk_instructorcurso_curso"))
    @JsonBackReference
    private Curso curso;

    /**
     * Fecha en que se otorgó la asignación del instructor al curso.
     */
    @NotNull
    @Column(name = "fecha_otorgacion", nullable = false)
    private LocalDate fechaOtorgacion;

    /**
     * Constructor para crear una asignación con generación automática de ID.
     * @param instructorId identificador del instructor
     * @param curso curso asociado
     * @param fechaOtorgacion fecha de asignación
     */
    public InstructorCurso(@NotNull Long instructorId, @NotNull Curso curso, @NotNull LocalDate fechaOtorgacion) {
        this.instructorId = instructorId;
        this.curso = curso;
        this.fechaOtorgacion = fechaOtorgacion;
    }
    
}
