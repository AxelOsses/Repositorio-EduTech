package com.edutech.cursos_inscripciones_service.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "progreso_curso")
public class ProgresoCurso {
    @Id
    @Column(name = "id_progreso_curso", length = 36, nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "id_inscripcion", nullable = false, foreignKey = @ForeignKey(name = "fk_progreso_curso_inscripcion"))
    private Inscripcion inscripcion;

    @NotNull
    @Column(name = "porcentaje_avance", nullable = false)
    private Integer porcentajeAvance;

    @NotNull
    @Column(name = "tiempo_total_estudio", nullable = false)
    private Long tiempoTotalEstudio;

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
     * @param inscripcion inscripcion al que pertenece la informacion de avance
     * @param porcentajeAvance porcentaje del avance del curso, 
     *                         es la divicion de los modulos aprovados dividido en los modulos totales del curso
     * @param tiempoTotalEstudio tiempo total en el que el alumno a permanecido realizando el curso
     */
    public ProgresoCurso(@NotNull Inscripcion inscripcion, @NotNull Integer porcentajeAvance,
            @NotNull Long tiempoTotalEstudio) {
        this.id = UUID.randomUUID();
        this.inscripcion = inscripcion;
        this.porcentajeAvance = porcentajeAvance;
        this.tiempoTotalEstudio = tiempoTotalEstudio;
    }

    
}
