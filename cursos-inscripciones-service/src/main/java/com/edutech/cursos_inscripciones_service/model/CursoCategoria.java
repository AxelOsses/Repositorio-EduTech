package com.edutech.cursos_inscripciones_service.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "curso_categoria", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_curso", "id_categoria"})
    })
public class CursoCategoria {

    /**
     * Identificador único del CursoCategoria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental en MySQL
    @Column(name = "id_curso_categoria", updatable = false, nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_curso", nullable = false, 
                foreignKey = @ForeignKey(name = "fk_curso_categoria_curso"))
    @JsonBackReference
    private Curso curso;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_categoria", nullable = false, 
                foreignKey = @ForeignKey(name = "fk_curso_categoria_categoria"))
    @JsonBackReference
    private Categoria categoria;

    /**
     * Constructor para crear una asignación con generación automática de ID.
     * @param curso curso que posee la categoria en cuestion
     * @param categoria categoria al que pertenece el curso en cuestion
     */
    public CursoCategoria(@NotNull Curso curso, @NotNull Categoria categoria) {
        this.curso = curso;
        this.categoria = categoria;
    }

    /**
     * Compara esta instancia de CursoCategoria con otro objeto para determinar su igualdad.
     * Dos instancias son consideradas iguales si ambas contienen la misma referencia
     * de Curso y la misma referencia de Categoria.
     *
     * @param o el objeto a comparar con esta instancia
     * @return true si ambos objetos representan la misma relación Curso-Categoria, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CursoCategoria)) return false;
        CursoCategoria that = (CursoCategoria) o;
        return Objects.equals(curso, that.curso) &&
            Objects.equals(categoria, that.categoria);
    }

    /**
     * Calcula el código hash de esta instancia basado en las referencias de Curso y Categoria.
     * Este método asegura que instancias iguales (según equals) produzcan el mismo código hash.
     *
     * @return el código hash de esta instancia
     */
    @Override
    public int hashCode() {
        return Objects.hash(curso, categoria);
    }


}
