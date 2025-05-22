package com.edutech.cursos_service.model;

import java.time.Duration;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Curso {

    private int id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
    private Duration duracionHoras;
    private int numeroOrden;

}
