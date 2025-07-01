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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "modulo_cursado")
public class ModuloCursado {

    /**
     * Identificador único del ModuloCursado.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental en MySQL
    @Column(name = "id_modulo_cursado", updatable = false, nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_progreso_curso", nullable = false,
                foreignKey = @ForeignKey(name = "fk_modulo_cursado_progreso_curso"))
    @JsonBackReference
    private ProgresoCurso progresoCurso;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_modulo", nullable = false,
                foreignKey = @ForeignKey(name = "fk_modulo_cursado_modulo"))
    @JsonBackReference
    private Modulo modulo;

    @NotNull
    @Column(name = "esta_aprobado", nullable = false)
    private Boolean estaAprobado;

    @Column(name = "fecha_aprovacion")
    private LocalDate fechaAprobacion;

    @OneToOne(mappedBy = "moduloCursado")
    @JsonManagedReference
    private NotaEvaluacion notaEvaluacion;

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
        this.progresoCurso = progresoCurso;
        this.modulo = modulo;
        this.estaAprobado = estaAprovado;
        this.fechaAprobacion = fechaAprobacion;
        this.notaEvaluacion = notaEvaluacion;
    }

    
}
