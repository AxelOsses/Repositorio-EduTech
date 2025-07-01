package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.Curso;
import com.edutech.cursos_inscripciones_service.service.CursoService;
import com.edutech.cursos_inscripciones_service.exception.ResourceNotFoundException;
import com.edutech.cursos_inscripciones_service.exception.ConflictException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> getAllCursos() {
        List<Curso> cursos = cursoService.getAllCursos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> getCursoById(@PathVariable("id") Long id) {
        return cursoService.getCursoById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));
    }

    @PostMapping
    public ResponseEntity<Curso> createCurso(@Valid @RequestBody Curso curso) {
        if (curso.getId() != null && cursoService.getCursoById(curso.getId()).isPresent()) {
            throw new ConflictException("Ya existe un curso con id: " + curso.getId());
        }
        Curso created = cursoService.saveCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> updateCurso(@PathVariable("id") Long id, @Valid @RequestBody Curso updatedCurso) {
        if (!cursoService.getCursoById(id).isPresent()) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + id);
        }
        updatedCurso.setId(id);
        Curso updated = cursoService.saveCurso(updatedCurso);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable("id") Long id) {
        if (!cursoService.getCursoById(id).isPresent()) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + id);
        }
        cursoService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }
}

