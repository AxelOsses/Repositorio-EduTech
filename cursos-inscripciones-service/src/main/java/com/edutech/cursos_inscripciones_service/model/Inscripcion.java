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

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "inscripcion")
public class Inscripcion {

    /**
     * Identificador único de la inscripción.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental en MySQL
    @Column(name = "id_inscripcion", updatable = false, nullable = false)
    @Schema(description = "Identificador único de la inscripción", example = "1")
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_curso", nullable = false, foreignKey = @ForeignKey(name = "fk_inscripcion_curso"))
    @JsonBackReference
    @Schema(description = "Curso asociado a la inscripción")
    private Curso curso;

    @NotNull
    @Column(name = "id_usuario", nullable = false)
    @Schema(description = "ID del estudiante inscrito", example = "10")
    private Long estudianteId;

    @NotNull
    @Column(name = "fecha_inscripcion", nullable = false)
    @Schema(description = "Fecha de inscripción (yyyy-MM-dd)", example = "2023-05-01")
    private LocalDate fechaInscripcion;

    @NotNull
    @Column(name = "esta_aprobado", nullable = false)
    @Schema(description = "Indica si el curso fue aprobado", example = "true")
    private Boolean estaAprobado;

    /**
     * Constructor para crear una asignación con generación automática de ID.
     * @param curso curso al que pertenece la inscripcion
     * @param estudianteId id de usuario al que pertenece la inscripcion
     * @param fechaInscripcion fecha en que se realizó la inscripción
     * @param estaAprovado true si el curso esta aprovado y false si no lo esta
     */
    public Inscripcion(@NotNull Curso curso, @NotNull Long estudianteId, @NotNull LocalDate fechaInscripcion,
            @NotNull Boolean estaAprovado) {
        this.curso = curso;
        this.estudianteId = estudianteId;
        this.fechaInscripcion = fechaInscripcion;
        this.estaAprobado = estaAprovado;
    }

    
}
