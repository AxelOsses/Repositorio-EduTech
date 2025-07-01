package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.InstructorCurso;
import com.edutech.cursos_inscripciones_service.service.InstructorCursoService;
import com.edutech.cursos_inscripciones_service.exception.ResourceNotFoundException;
import com.edutech.cursos_inscripciones_service.exception.ConflictException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instructor-cursos")
public class InstructorCursoController {

    private final InstructorCursoService instructorCursoService;

    public InstructorCursoController(InstructorCursoService instructorCursoService) {
        this.instructorCursoService = instructorCursoService;
    }

    @GetMapping
    public ResponseEntity<List<InstructorCurso>> getAllInstructoresCursos() {
        List<InstructorCurso> instructores = instructorCursoService.getAllInstructoresCursos();
        return ResponseEntity.ok(instructores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorCurso> getInstructorCursoById(@PathVariable("id") Long id) {
        return instructorCursoService.getInstructorCursoById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("InstructorCurso no encontrado con id: " + id));
    }

    @PostMapping
    public ResponseEntity<InstructorCurso> createInstructorCurso(@Valid @RequestBody InstructorCurso instructorCurso) {
        if (instructorCurso.getId() != null && instructorCursoService.getInstructorCursoById(instructorCurso.getId()).isPresent()) {
            throw new ConflictException("Ya existe un InstructorCurso con id: " + instructorCurso.getId());
        }
        InstructorCurso created = instructorCursoService.saveInstructorCurso(instructorCurso);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorCurso> updateInstructorCurso(@PathVariable("id") Long id, @Valid @RequestBody InstructorCurso updatedInstructorCurso) {
        if (!instructorCursoService.getInstructorCursoById(id).isPresent()) {
            throw new ResourceNotFoundException("InstructorCurso no encontrado con id: " + id);
        }
        updatedInstructorCurso.setId(id);
        InstructorCurso updated = instructorCursoService.saveInstructorCurso(updatedInstructorCurso);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructorCurso(@PathVariable("id") Long id) {
        if (!instructorCursoService.getInstructorCursoById(id).isPresent()) {
            throw new ResourceNotFoundException("InstructorCurso no encontrado con id: " + id);
        }
        instructorCursoService.deleteInstructorCurso(id);
        return ResponseEntity.noContent().build();
    }
}

