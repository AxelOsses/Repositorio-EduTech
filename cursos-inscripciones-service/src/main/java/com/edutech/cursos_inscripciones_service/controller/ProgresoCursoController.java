package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.ProgresoCurso;
import com.edutech.cursos_inscripciones_service.service.ProgresoCursoService;

import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/progresos-curso")
public class ProgresoCursoController {

    private final ProgresoCursoService progresoCursoService;

    @Autowired
    public ProgresoCursoController(ProgresoCursoService progresoCursoService) {
        this.progresoCursoService = progresoCursoService;
    }

    @GetMapping
    public List<ProgresoCurso> getAllProgresosCursos() {
        return progresoCursoService.getAllProgresoCursos();
    }

    @GetMapping("/{id}")
    public Optional<ProgresoCurso> getProgresoCursoById(@PathVariable("id") Long id) {
        return progresoCursoService.getProgresoCursoById(id);
    }

    @PostMapping
    public ProgresoCurso createProgresoCurso(@RequestBody ProgresoCurso progresoCurso) {
        return progresoCursoService.saveProgresoCurso(progresoCurso);
    }

    @PutMapping("/{id}")
    public ProgresoCurso updateProgresoCurso(@PathVariable("id") Long id, @RequestBody ProgresoCurso updatedProgresoCurso) {
        updatedProgresoCurso.setId(id);
        return progresoCursoService.saveProgresoCurso(updatedProgresoCurso);
    }

    @DeleteMapping("/{id}")
    public void deleteProgresoCurso(@PathVariable("id") Long id) {
        progresoCursoService.deleteProgresoCurso(id);
    }
}

