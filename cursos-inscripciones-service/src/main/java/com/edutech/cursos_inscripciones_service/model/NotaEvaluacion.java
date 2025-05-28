package com.edutech.cursos_inscripciones_service.model;

import java.util.UUID;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "nota_evaluacion")
public class NotaEvaluacion {
    @Id
    @Column(name = "id_nota_evaluacion", nullable = false, updatable = false, length = 36)
    private UUID id;

    @OneToOne(optional = true)
    @JoinColumn(name = "id_modulo_cursado", foreignKey = @ForeignKey(name = "fk_nota_evaluacion_modulo_cursado"))
    private ModuloCursado moduloCursado;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_evaluacion", nullable = false,
                foreignKey = @ForeignKey(name = "fk_nota_evaluacion_evaluacion"))
    private Evaluacion evaluacion;

    @Column(name = "puntaje_obtenido")
    private Float puntajeObtenido;

    @NonNull
    @Column(name = "puntaje_requerido", nullable = false)
    private Float puntajeRequerido;

    @Size(max = 3000)
    @Column(length = 3000)
    private String comentario;

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
     * @param moduloCursado modulo que se esta cursando al que le pertenece la nota de la evaluacion
     * @param evaluacion evaluacion al que pertenece la nota
     * @param puntajeObtenido puntaje obtenido en la evaluacion
     * @param puntajeRequerido puntaje minimo que se requiere para que se apruebe la evaluacion
     * @param comentario feekback del profesor quien califica
     */
    public NotaEvaluacion(ModuloCursado moduloCursado, Evaluacion evaluacion, Float puntajeObtenido,
            Float puntajeRequerido, @Size(max = 3000) String comentario) {
        this.id = UUID.randomUUID();
        this.moduloCursado = moduloCursado;
        this.evaluacion = evaluacion;
        this.puntajeObtenido = puntajeObtenido;
        this.puntajeRequerido = puntajeRequerido;
        this.comentario = comentario;
    }

    
}
