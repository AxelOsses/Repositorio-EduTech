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

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "inscripcion")
public class Inscripcion {
    @Id
    @Column(name = "id_inscripcion", length = 36, nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_curso", nullable = false, foreignKey = @ForeignKey(name = "fk_inscripcion_curso"))
    private Curso curso;

    @NotNull
    @Column(name = "id_usuario", nullable = false)
    private UUID estudianteId;

    @NotNull
    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDate fechaInscripcion;

    @NotNull
    @Column(name = "esta_aprobado", nullable = false)
    private Boolean estaAprobado;

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
     * @param curso curso al que pertenece la inscripcion
     * @param estudianteId id de usuario al que pertenece la inscripcion
     * @param fechaInscripcion fecha en que se realizó la inscripción
     * @param estaAprovado true si el curso esta aprovado y false si no lo esta
     */
    public Inscripcion(@NotNull Curso curso, @NotNull UUID estudianteId, @NotNull LocalDate fechaInscripcion,
            @NotNull Boolean estaAprovado) {
        this.id = UUID.randomUUID();
        this.curso = curso;
        this.estudianteId = estudianteId;
        this.fechaInscripcion = fechaInscripcion;
        this.estaAprobado = estaAprovado;
    }

    
}
