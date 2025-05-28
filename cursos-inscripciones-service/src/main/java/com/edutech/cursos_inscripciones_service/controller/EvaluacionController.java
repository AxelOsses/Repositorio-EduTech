package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.Evaluacion;
import com.edutech.cursos_inscripciones_service.service.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    @Autowired
    public EvaluacionController(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    @GetMapping
    public List<Evaluacion> getAllEvaluaciones() {
        return evaluacionService.getAllEvaluaciones();
    }

    @GetMapping("/{id}")
    public Optional<Evaluacion> getEvaluacionById(@PathVariable UUID id) {
        return evaluacionService.getEvaluacionById(id);
    }

    @PostMapping
    public Evaluacion createEvaluacion(@RequestBody Evaluacion evaluacion) {
        return evaluacionService.saveEvaluacion(evaluacion);
    }

    @PutMapping("/{id}")
    public Evaluacion updateEvaluacion(@PathVariable UUID id, @RequestBody Evaluacion updatedEvaluacion) {
        updatedEvaluacion.setId(id);
        return evaluacionService.saveEvaluacion(updatedEvaluacion);
    }

    @DeleteMapping("/{id}")
    public void deleteEvaluacion(@PathVariable UUID id) {
        evaluacionService.deleteEvaluacion(id);
    }
}
