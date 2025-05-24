package com.edutech.cursos_inscripciones_service.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.ForeignKey;


@Entity
@Table(name = "instructor_curso")
public class InstructorCurso {
    
    @Id
    @Column(name = "id_instructor_curso", length = 36, updatable = false, nullable = false)
    private UUID id = UUID.randomUUID(); 

    // ID del instructor que proviene del microservicio usuarios-service
    @NotNull
    @Column(name = "id_usuario", nullable = false)
    private UUID instructorId;

    //Relación con la entidad local Curso (llave foránea hacia Curso.id)
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_curso", nullable = false,
                foreignKey = @ForeignKey(name = "fk_instructorcurso_curso"))// se enlaza con Curso.id
    private Curso curso;

    @NotNull
    @Column(name = "fecha_otorgacion", nullable = false)
    private LocalDate fechaOtorgacion;
    
    public InstructorCurso() {
    }

    public InstructorCurso(UUID instructorId, Curso curso, LocalDate fechaOtorgacion) {
        this.instructorId = instructorId;
        this.curso = curso;
        this.fechaOtorgacion = fechaOtorgacion;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(UUID instructorId) {
        this.instructorId = instructorId;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDate getFechaOtorgacion() {
        return fechaOtorgacion;
    }

    public void setFechaOtorgacion(LocalDate fechaOtorgacion) {
        this.fechaOtorgacion = fechaOtorgacion;
    }

    
}
