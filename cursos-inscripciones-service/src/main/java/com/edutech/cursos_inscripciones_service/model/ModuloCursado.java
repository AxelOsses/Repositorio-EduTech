package com.edutech.cursos_inscripciones_service.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "modulo_cursado")
public class ModuloCursado {
    @Id
    @Column(name = "id_modulo_cursado", nullable = false, updatable = false, length = 36)
    private UUID id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_progreso_curso", nullable = false,
                foreignKey = @ForeignKey(name = "fk_modulo_cursado_progreso_curso"))
    private ProgresoCurso progresoCurso;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_modulo", nullable = false,
                foreignKey = @ForeignKey(name = "fk_modulo_cursado_modulo"))
    private Modulo modulo;

    @NotNull
    @Column(name = "esta_aprobado", nullable = false)
    private Boolean estaAprobado;

    @Column(name = "fecha_aprovacion")
    private LocalDate fechaAprobacion;

    @OneToOne(mappedBy = "moduloCursado")
    private NotaEvaluacion notaEvaluacion;

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
     * @param progresoCurso progresoCurso al que pertenece moduloCursado
     * @param modulo modulo que se esta cursando
     * @param estaAprobado true si el modulo esta aprovado, false si no lo esta
     * @param fechaAprovacion fecha en la que se aprovo el modulo
     * @param notaEvaluacion nota con que se aprovo el modulo
     */
    public ModuloCursado(@NotNull ProgresoCurso progresoCurso, @NotNull Modulo modulo, @NotNull Boolean estaAprovado,
            LocalDate fechaAprobacion, NotaEvaluacion notaEvaluacion) {
        this.id = UUID.randomUUID();        
        this.progresoCurso = progresoCurso;
        this.modulo = modulo;
        this.estaAprobado = estaAprovado;
        this.fechaAprobacion = fechaAprobacion;
        this.notaEvaluacion = notaEvaluacion;
    }

    
}
