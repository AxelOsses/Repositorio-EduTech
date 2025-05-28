package com.edutech.cursos_inscripciones_service.model;

import com.edutech.cursos_inscripciones_service.exception.ModuloDuplicadoException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un módulo dentro de un curso.
 * Contiene información como el título, descripción, orden y relación con el curso.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "modulo")
public class Modulo {

    /**
     * Identificador único del modulo.
     */
    /**
     * Identificador único del curso.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental en MySQL
    @Column(name = "id_modulo", updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotNull
    @Min(1)
    @Column(name = "numero_orden", nullable = false)
    private Integer numeroOrden;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_curso", nullable = false,
                foreignKey = @ForeignKey(name = "fk_modulo_curso"))
    private Curso curso;

    /**
     * Constructor con generación automática de ID.
     * @param titulo el título del módulo
     * @param descripcion la descripción
     * @param numeroOrden el orden del módulo
     * @param curso el curso asociado
     */
    public Modulo(String titulo, String descripcion, Integer numeroOrden, Curso curso) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.numeroOrden = numeroOrden;
        this.curso = curso;
    }

    /**
     * Asigna un curso al módulo, asegurando mantener la relación bidireccional.
     * @param curso el curso a asignar
     * @throws ModuloDuplicadoException si el módulo ya existe en el curso
     */
    public void setCurso(Curso curso) {
        if (this.curso != null) {
            this.curso.getModulos().remove(this);
        }
        this.curso = curso;
        if (curso != null) {
            if (!curso.getModulos().contains(this)) {
                curso.getModulos().add(this);
            } else {
                throw new ModuloDuplicadoException("El módulo ya existe en el curso.");
            }
        }
    }

    /**
     * Verifica si el módulo es igual a otro por su ID.
     * @param o el objeto a comparar
     * @return true si tienen el mismo ID
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Modulo modulo = (Modulo) o;
        return id != null && id.equals(modulo.id);
    }

    /**
     * Genera el código hash basado en el ID del módulo.
     * @return el hashcode
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
