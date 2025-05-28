package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.ProgresoCurso;
import com.edutech.cursos_inscripciones_service.service.ProgresoCursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<ProgresoCurso> getProgresoCursoById(@PathVariable UUID id) {
        return progresoCursoService.getProgresoCursoById(id);
    }

    @PostMapping
    public ProgresoCurso createProgresoCurso(@RequestBody ProgresoCurso progresoCurso) {
        return progresoCursoService.saveProgresoCurso(progresoCurso);
    }

    @PutMapping("/{id}")
    public ProgresoCurso updateProgresoCurso(@PathVariable UUID id, @RequestBody ProgresoCurso updatedProgresoCurso) {
        updatedProgresoCurso.setId(id);
        return progresoCursoService.saveProgresoCurso(updatedProgresoCurso);
    }

    @DeleteMapping("/{id}")
    public void deleteProgresoCurso(@PathVariable UUID id) {
        progresoCursoService.deleteProgresoCurso(id);
    }
}

