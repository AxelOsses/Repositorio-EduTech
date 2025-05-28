package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.NotaEvaluacion;
import com.edutech.cursos_inscripciones_service.service.NotaEvaluacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notas-evaluacion")
public class NotaEvaluacionController {

    private final NotaEvaluacionService notaEvaluacionService;

    @Autowired
    public NotaEvaluacionController(NotaEvaluacionService notaEvaluacionService) {
        this.notaEvaluacionService = notaEvaluacionService;
    }

    @GetMapping
    public List<NotaEvaluacion> getAllNotasEvaluaciones() {
        return notaEvaluacionService.getAllNotasEvaluaciones();
    }

    @GetMapping("/{id}")
    public Optional<NotaEvaluacion> getNotaEvaluacionById(@PathVariable("id") Long id) {
        return notaEvaluacionService.getNotaEvaluacionById(id);
    }

    @PostMapping
    public NotaEvaluacion createNotaEvaluacion(@RequestBody NotaEvaluacion notaEvaluacion) {
        return notaEvaluacionService.saveNotaEvaluacion(notaEvaluacion);
    }

    @PutMapping("/{id}")
    public NotaEvaluacion updateNotaEvaluacion(@PathVariable("id") Long id, @RequestBody NotaEvaluacion updatedNotaEvaluacion) {
        updatedNotaEvaluacion.setId(id);
        return notaEvaluacionService.saveNotaEvaluacion(updatedNotaEvaluacion);
    }

    @DeleteMapping("/{id}")
    public void deleteNotaEvaluacion(@PathVariable("id") Long id) {
        notaEvaluacionService.deleteNotaEvaluacion(id);
    }
}
