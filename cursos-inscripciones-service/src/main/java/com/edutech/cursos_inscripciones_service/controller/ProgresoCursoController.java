package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.ProgresoCurso;
import com.edutech.cursos_inscripciones_service.service.ProgresoCursoService;
import com.edutech.cursos_inscripciones_service.exception.ResourceNotFoundException;
import com.edutech.cursos_inscripciones_service.exception.ConflictException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/progresos-curso")
public class ProgresoCursoController {

    private final ProgresoCursoService progresoCursoService;

    public ProgresoCursoController(ProgresoCursoService progresoCursoService) {
        this.progresoCursoService = progresoCursoService;
    }

    @GetMapping
    public ResponseEntity<List<ProgresoCurso>> getAllProgresosCursos() {
        List<ProgresoCurso> progresos = progresoCursoService.getAllProgresoCursos();
        return ResponseEntity.ok(progresos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgresoCurso> getProgresoCursoById(@PathVariable("id") Long id) {
        return progresoCursoService.getProgresoCursoById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("ProgresoCurso no encontrado con id: " + id));
    }

    @PostMapping
    public ResponseEntity<ProgresoCurso> createProgresoCurso(@Valid @RequestBody ProgresoCurso progresoCurso) {
        if (progresoCurso.getId() != null && progresoCursoService.getProgresoCursoById(progresoCurso.getId()).isPresent()) {
            throw new ConflictException("Ya existe un ProgresoCurso con id: " + progresoCurso.getId());
        }
        ProgresoCurso created = progresoCursoService.saveProgresoCurso(progresoCurso);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgresoCurso> updateProgresoCurso(@PathVariable("id") Long id, @Valid @RequestBody ProgresoCurso updatedProgresoCurso) {
        if (!progresoCursoService.getProgresoCursoById(id).isPresent()) {
            throw new ResourceNotFoundException("ProgresoCurso no encontrado con id: " + id);
        }
        updatedProgresoCurso.setId(id);
        ProgresoCurso updated = progresoCursoService.saveProgresoCurso(updatedProgresoCurso);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgresoCurso(@PathVariable("id") Long id) {
        if (!progresoCursoService.getProgresoCursoById(id).isPresent()) {
            throw new ResourceNotFoundException("ProgresoCurso no encontrado con id: " + id);
        }
        progresoCursoService.deleteProgresoCurso(id);
        return ResponseEntity.noContent().build();
    }
}

