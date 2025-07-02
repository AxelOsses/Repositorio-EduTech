package com.edutech.cursos_inscripciones_service.model;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "nota_evaluacion")
public class NotaEvaluacion {

    /**
     * Identificador único de notaEvaluacion.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental en MySQL
    @Column(name = "id_nota_evaluacion", updatable = false, nullable = false)
    @Schema(description = "Identificador único de la nota de evaluación", example = "1")
    private Long id;

    @OneToOne(optional = true)
    @JoinColumn(name = "id_modulo_cursado", foreignKey = @ForeignKey(name = "fk_nota_evaluacion_modulo_cursado"))
    @JsonBackReference
    @Schema(description = "Módulo cursado asociado a la nota de evaluación")
    private ModuloCursado moduloCursado;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_evaluacion", nullable = false,
                foreignKey = @ForeignKey(name = "fk_nota_evaluacion_evaluacion"))
    @JsonBackReference
    @Schema(description = "Evaluación asociada a la nota de evaluación")
    private Evaluacion evaluacion;

    @Column(name = "puntaje_obtenido")
    @Schema(description = "Puntaje obtenido en la evaluación", example = "8.5")
    private Float puntajeObtenido;

    @NonNull
    @Column(name = "puntaje_requerido", nullable = false)
    @Schema(description = "Puntaje requerido para aprobar la evaluación", example = "7.0")
    private Float puntajeRequerido;

    @Size(max = 3000)
    @Column(length = 3000)
    @Schema(description = "Comentario o feedback del profesor", example = "Buen desempeño, pero puedes mejorar en la parte teórica.")
    private String comentario;

    /**
     * Constructor para crear una asignación con generación automática de ID.
     * @param moduloCursado modulo que se esta cursando al que le pertenece la nota de la evaluacion
     * @param evaluacion evaluacion al que pertenece la nota
     * @param puntajeObtenido puntaje obtenido en la evaluacion
     * @param puntajeRequerido puntaje minimo que se requiere para que se apruebe la evaluacion
     * @param comentario feekback del profesor quien califica
     */
    public NotaEvaluacion(ModuloCursado moduloCursado, Evaluacion evaluacion, Float puntajeObtenido,
            Float puntajeRequerido, @Size(max = 3000) String comentario) {
        this.moduloCursado = moduloCursado;
        this.evaluacion = evaluacion;
        this.puntajeObtenido = puntajeObtenido;
        this.puntajeRequerido = puntajeRequerido;
        this.comentario = comentario;
    }

    
}
