package com.edutech.cursos_inscripciones_service.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.edutech.cursos_inscripciones_service.exception.ModuloDuplicadoException;
import com.edutech.cursos_inscripciones_service.exception.ModuloInexistenteException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un curso en la plataforma.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "curso")
public class Curso {

    /**
     * Identificador único del curso (UUID).
     */
    @Id
    @Column(name = "id_curso", length = 36, updatable = false, nullable = false)
    private UUID id;

    /**
     * Título del curso.
     */
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String titulo;

    /**
     * Descripción detallada del curso.
     */
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    /**
     * Fecha de creación del curso.
     */
    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    /**
     * Fecha de última actualización del curso.
     */
    @Column(name = "fecha_actualizacion")
    private LocalDate fechaActualizacion;

    /**
     * Duración total del curso en horas.
     */
    @Min(1)
    @Column(name = "duracion_horas")
    private int duracionHoras;

    /**
     * Número de orden del curso en la lista general.
     */
    @NotNull
    @Min(1)
    @Column(name = "numero_orden")
    private Integer numeroOrden;

    /**
     * Estado actual del curso (activo, inactivo, archivado, etc.).
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEstadoCurso estado;

    /**
     * Lista de módulos asociados al curso.
     * Relación bidireccional con la entidad Modulo.
     */
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("numeroOrden ASC")
    private List<Modulo> modulos = new ArrayList<>();

    /**
     * Inicializa automáticamente el ID y la fecha de creación antes de persistir.
     */
    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (fechaCreacion == null) {
            fechaCreacion = LocalDate.now();
        }
    }

    /**
     * Constructor con generación automática del ID.
     *
     * @param titulo título del curso
     * @param descripcion descripción del curso
     * @param fechaCreacion fecha de creación
     * @param fechaActualizacion fecha de actualización
     * @param duracionHoras duración total en horas
     * @param numeroOrden número de orden
     * @param estado estado del curso
     */
    public Curso(String titulo, String descripcion, LocalDate fechaCreacion, LocalDate fechaActualizacion,
                 int duracionHoras, Integer numeroOrden, TipoEstadoCurso estado) {
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.duracionHoras = duracionHoras;
        this.numeroOrden = numeroOrden;
        this.estado = estado;
    }

    /**
     * Agrega un módulo al curso si no está presente.
     *
     * @param modulo el módulo a agregar
     * @throws IllegalArgumentException si el módulo es null
     * @throws ModuloDuplicadoException si el módulo ya existe en el curso
     */
    public void agregarModulo(Modulo modulo) {
        if (modulo == null) {
            throw new IllegalArgumentException("El módulo no puede ser null.");
        }
        if (modulos.contains(modulo)) {
            throw new ModuloDuplicadoException("El módulo con título '" + modulo.getTitulo() + "' ya existe en el curso.");
        }
        modulos.add(modulo);
        modulo.setCurso(this);
    }

    /**
     * Elimina un módulo del curso si existe.
     *
     * @param modulo el módulo a eliminar
     * @throws IllegalArgumentException si el módulo es null
     * @throws ModuloInexistenteException si el módulo no existe en el curso
     */
    public void eliminarModulo(Modulo modulo) {
        if (modulo == null) {
            throw new IllegalArgumentException("El módulo no puede ser null.");
        }
        if (!modulos.contains(modulo)) {
            throw new ModuloInexistenteException("El módulo con título '" + modulo.getTitulo() + "' no existe en el curso.");
        }
        modulos.remove(modulo);
        modulo.setCurso(null);
    }

    /**
     * Compara dos cursos por su ID.
     *
     * @param o el objeto a comparar
     * @return true si tienen el mismo ID
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        return id != null && id.equals(curso.id);
    }

    /**
     * Genera el hashCode del curso basado en su ID.
     *
     * @return el código hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
