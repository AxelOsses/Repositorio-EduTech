package com.edutech.cursos_inscripciones_service.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "id_instructor_curso", length = 36, nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    /**
     * Identificador del instructor, proveniente del microservicio usuarios-service.
     */
    @NotNull
    @Column(name = "id_usuario", nullable = false)
    private UUID instructorId;

    /**
     * Curso asociado (relación local hacia Curso.id).
     */
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_curso", nullable = false,
                foreignKey = @ForeignKey(name = "fk_instructorcurso_curso"))
    private Curso curso;

    /**
     * Fecha en que se otorgó la asignación del instructor al curso.
     */
    @NotNull
    @Column(name = "fecha_otorgacion", nullable = false)
    private LocalDate fechaOtorgacion;

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
     * @param instructorId identificador del instructor
     * @param curso curso asociado
     * @param fechaOtorgacion fecha de asignación
     */
    public InstructorCurso(UUID instructorId, Curso curso, LocalDate fechaOtorgacion) {
        this.id = UUID.randomUUID();
        this.instructorId = instructorId;
        this.curso = curso;
        this.fechaOtorgacion = fechaOtorgacion;
    }
}
