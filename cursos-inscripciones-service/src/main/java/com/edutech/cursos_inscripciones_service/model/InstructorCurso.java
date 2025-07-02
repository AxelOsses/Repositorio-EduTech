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
import io.swagger.v3.oas.annotations.media.Schema;

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
    @Schema(description = "Identificador único de la asignación instructor-curso", example = "1")
    private Long id;

    /**
     * Identificador del instructor, proveniente del microservicio usuarios-service.
     */
    @NotNull
    @Column(name = "id_usuario", nullable = false)
    @Schema(description = "ID del instructor asignado", example = "20")
    private Long instructorId;

    /**
     * Curso asociado (relación local hacia Curso.id).
     */
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_curso", nullable = false,
                foreignKey = @ForeignKey(name = "fk_instructorcurso_curso"))
    @JsonBackReference
    @Schema(description = "Curso asociado a la asignación")
    private Curso curso;

    /**
     * Fecha en que se otorgó la asignación del instructor al curso.
     */
    @NotNull
    @Column(name = "fecha_otorgacion", nullable = false)
    @Schema(description = "Fecha de otorgación de la asignación (yyyy-MM-dd)", example = "2023-04-10")
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

    // Métodos manuales para evitar problemas con Lombok
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
