package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.Curso;
import com.edutech.cursos_inscripciones_service.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {

    private final CursoService cursoService;

    @Autowired
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public List<Curso> getAllCursos() {
        return cursoService.getAllCursos();
    }

    @GetMapping("/{id}")
    public Optional<Curso> getCursoById(@PathVariable("id") Long id) {
        return cursoService.getCursoById(id);
    }

    @PostMapping
    public Curso createCurso(@RequestBody Curso curso) {
        return cursoService.saveCurso(curso);
    }

    @PutMapping("/{id}")
    public Curso updateCurso(@PathVariable("id") Long id, @RequestBody Curso updatedCurso) {
        updatedCurso.setId(id);
        return cursoService.saveCurso(updatedCurso);
    }

    @DeleteMapping("/{id}")
    public void deleteCurso(@PathVariable("id") Long id) {
        cursoService.deleteCurso(id);
    }
}

