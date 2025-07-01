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
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import com.edutech.cursos_inscripciones_service.exception.ModuloCursadoDuplicadoException;
import com.edutech.cursos_inscripciones_service.exception.ModuloCursadoInexistenteException;

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
    @Schema(description = "Identificador único del progreso de curso", example = "1")
    private Long id;

    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "id_inscripcion", nullable = false, foreignKey = @ForeignKey(name = "fk_progreso_curso_inscripcion"))
    @Schema(description = "Inscripción asociada al progreso de curso")
    private Inscripcion inscripcion;

    @NotNull
    @Column(name = "porcentaje_avance", nullable = false)
    @Schema(description = "Porcentaje de avance en el curso", example = "80")
    private Integer porcentajeAvance;

    @NotNull
    @Column(name = "tiempo_total_estudio", nullable = false)
    @Schema(description = "Tiempo total de estudio en minutos", example = "1200")
    private Long tiempoTotalEstudio;

    @OneToMany(mappedBy = "progresoCurso", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Schema(description = "Lista de módulos cursados asociados a este progreso de curso")
    private List<ModuloCursado> modulosCursados = new ArrayList<>();

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

    /**
     * Agrega un módulo cursado al progreso de curso si no está presente.
     *
     * @param moduloCursado el módulo cursado a agregar
     * @throws IllegalArgumentException si el módulo cursado es null
     * @throws ModuloCursadoDuplicadoException si el módulo cursado ya existe en el progreso
     */
    public void agregarModuloCursado(ModuloCursado moduloCursado) {
        if (moduloCursado == null) {
            throw new IllegalArgumentException("El módulo cursado no puede ser null.");
        }
        if (modulosCursados.contains(moduloCursado)) {
            throw new ModuloCursadoDuplicadoException("El módulo cursado ya existe en el progreso de curso.");
        }
        modulosCursados.add(moduloCursado);
        moduloCursado.setProgresoCurso(this);
    }

    /**
     * Elimina un módulo cursado del progreso de curso si existe.
     *
     * @param moduloCursado el módulo cursado a eliminar
     * @throws IllegalArgumentException si el módulo cursado es null
     * @throws ModuloCursadoInexistenteException si el módulo cursado no existe en el progreso
     */
    public void eliminarModuloCursado(ModuloCursado moduloCursado) {
        if (moduloCursado == null) {
            throw new IllegalArgumentException("El módulo cursado no puede ser null.");
        }
        if (!modulosCursados.contains(moduloCursado)) {
            throw new ModuloCursadoInexistenteException("El módulo cursado no existe en el progreso de curso.");
        }
        modulosCursados.remove(moduloCursado);
        moduloCursado.setProgresoCurso(null);
    }
}
