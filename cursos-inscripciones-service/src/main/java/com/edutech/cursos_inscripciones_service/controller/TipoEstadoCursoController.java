package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.TipoEstadoCurso;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tipos-estado-curso")
public class TipoEstadoCursoController {

    /**
     * Devuelve todos los posibles estados de curso.
     */
    @GetMapping
    public TipoEstadoCurso[] getAllTiposEstadoCurso() {
        return TipoEstadoCurso.values();
    }
}


