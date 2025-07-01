package com.edutech.cursos_inscripciones_service.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "progreso_curso")
public class ProgresoCurso {

    /**
     * Identificador único del ProgresoCurso.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental en MySQL
    @Column(name = "id_progreso_curso", updatable = false, nullable = false)
    private Long id;

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
     * Constructor para crear una asignación con generación automática de ID.
     * @param inscripcion inscripcion al que pertenece la informacion de avance
     * @param porcentajeAvance porcentaje del avance del curso, 
     *                         es la divicion de los modulos aprovados dividido en los modulos totales del curso
     * @param tiempoTotalEstudio tiempo total en el que el alumno a permanecido realizando el curso
     */
    public ProgresoCurso(@NotNull Inscripcion inscripcion, @NotNull Integer porcentajeAvance,
            @NotNull Long tiempoTotalEstudio) {
        this.inscripcion = inscripcion;
        this.porcentajeAvance = porcentajeAvance;
        this.tiempoTotalEstudio = tiempoTotalEstudio;
    }

    
}
