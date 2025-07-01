package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.Evaluacion;
import com.edutech.cursos_inscripciones_service.service.EvaluacionService;
import com.edutech.cursos_inscripciones_service.exception.ResourceNotFoundException;
import com.edutech.cursos_inscripciones_service.exception.ConflictException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    public EvaluacionController(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    @GetMapping
    public ResponseEntity<List<Evaluacion>> getAllEvaluaciones() {
        List<Evaluacion> evaluaciones = evaluacionService.getAllEvaluaciones();
        return ResponseEntity.ok(evaluaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evaluacion> getEvaluacionById(@PathVariable("id") Long id) {
        return evaluacionService.getEvaluacionById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluación no encontrada con id: " + id));
    }

    @PostMapping
    public ResponseEntity<Evaluacion> createEvaluacion(@Valid @RequestBody Evaluacion evaluacion) {
        if (evaluacion.getId() != null && evaluacionService.getEvaluacionById(evaluacion.getId()).isPresent()) {
            throw new ConflictException("Ya existe una evaluación con id: " + evaluacion.getId());
        }
        Evaluacion created = evaluacionService.saveEvaluacion(evaluacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evaluacion> updateEvaluacion(@PathVariable("id") Long id, @Valid @RequestBody Evaluacion updatedEvaluacion) {
        if (!evaluacionService.getEvaluacionById(id).isPresent()) {
            throw new ResourceNotFoundException("Evaluación no encontrada con id: " + id);
        }
        updatedEvaluacion.setId(id);
        Evaluacion updated = evaluacionService.saveEvaluacion(updatedEvaluacion);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluacion(@PathVariable("id") Long id) {
        if (!evaluacionService.getEvaluacionById(id).isPresent()) {
            throw new ResourceNotFoundException("Evaluación no encontrada con id: " + id);
        }
        evaluacionService.deleteEvaluacion(id);
        return ResponseEntity.noContent().build();
    }
}
