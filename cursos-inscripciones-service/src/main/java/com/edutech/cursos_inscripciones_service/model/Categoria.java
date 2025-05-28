package com.edutech.cursos_inscripciones_service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @Column(name = "id_categoria", nullable = false, updatable= false, length = 36)
    private UUID id;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255, unique = true)
    private String nombre;

    /**
     * Lista de cursos asociados a esta categoria.
     * Relación bidireccional con la entidad Curso.
     */
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CursoCategoria> cursoCategorias = new ArrayList<>();


    /**
     * Constructor para crear una asignación con generación automática de ID.
     * @param nombre nombre de la categoría
     */
    public Categoria(String nombre) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
    }

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
     * Asocia esta instancia de Categoria con un nuevo Curso mediante la creación
     * de una relación CursoCategoria. Esta operación también agrega la relación
     * a la lista de cursoCategorias del Curso correspondiente, asegurando
     * la consistencia bidireccional.
     *
     * @param curso el Curso con el que se desea asociar esta Categoria
     * @throws IllegalArgumentException si el curso es null
     */
    public void addCurso(Curso curso) {
        if (curso == null) {
            throw new IllegalArgumentException("El curso no puede ser null.");
        }
        CursoCategoria cc = new CursoCategoria(curso, this);
        cursoCategorias.add(cc);
        curso.getCursoCategorias().add(cc);
    }

    /**
     * Elimina la relación entre esta instancia de Categoria y el Curso especificado.
     * La operación también elimina la relación correspondiente de la lista de
     * cursoCategorias del Curso, manteniendo la consistencia bidireccional.
     *
     * @param curso el Curso del cual se desea eliminar la relación con esta Categoria
     * @throws IllegalArgumentException si el curso es null
     */
    public void removeCurso(Curso curso) {
        if (curso == null) {
            throw new IllegalArgumentException("El curso no puede ser null.");
        }
        // Remover la relación en la lista de cursoCategorias de esta Categoria
        cursoCategorias.removeIf(cc -> cc.getCurso().equals(curso));
        // Remover la relación en la lista de cursoCategorias del Curso
        curso.getCursoCategorias().removeIf(cc -> cc.getCategoria().equals(this));
    }


}
