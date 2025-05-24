package com.edutech.cursos_inscripciones_service.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "modulo")
public class Modulo {
    @Id
    @Column(name = "id_modulo", length = 36, nullable = false, updatable = false)
    private UUID id = UUID.randomUUID(); 

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotNull
    @Min(1)
    @Column(name = "numero_orden", nullable = false)
    private Integer numeroOrden;

    //Relacion con la Entidad local Curso (llave foranea hacia Curso.id)
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_curso", nullable = false,
                foreignKey = @ForeignKey(name = "fk_modulo_curso"))// Se enlaza con Curso.id
    private Curso curso;
    
    public Modulo() {
    }

    public Modulo(String titulo, String descripcion, Integer numeroOrden, Curso curso) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.numeroOrden = numeroOrden;
        this.curso = curso;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(Integer numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public Curso getCurso() {
        return curso;
    }

    //Pendiente
    public void setCurso(Curso curso) {
        if(curso != null){
            this.curso = curso;
        }
        
    }
    
}
