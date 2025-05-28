package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.TipoEvaluacion;
import com.edutech.cursos_inscripciones_service.service.TipoEvaluacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tipo-evaluaciones")
public class TipoEvaluacionController {

    private final TipoEvaluacionService tipoEvaluacionService;

    @Autowired
    public TipoEvaluacionController(TipoEvaluacionService tipoEvaluacionService) {
        this.tipoEvaluacionService = tipoEvaluacionService;
    }

    @GetMapping
    public List<TipoEvaluacion> getAllTipoEvaluaciones() {
        return tipoEvaluacionService.getAllTipoEvaluaciones();
    }

    @GetMapping("/{id}")
    public Optional<TipoEvaluacion> getTipoEvaluacionById(@PathVariable("id") Long id) {
        return tipoEvaluacionService.getTipoEvaluacionById(id);
    }

    @PostMapping
    public TipoEvaluacion createTipoEvaluacion(@RequestBody TipoEvaluacion tipoEvaluacion) {
        return tipoEvaluacionService.saveTipoEvaluacion(tipoEvaluacion);
    }

    @PutMapping("/{id}")
    public TipoEvaluacion updateTipoEvaluacion(@PathVariable("id") Long id, @RequestBody TipoEvaluacion updatedTipoEvaluacion) {
        updatedTipoEvaluacion.setId(id);
        return tipoEvaluacionService.saveTipoEvaluacion(updatedTipoEvaluacion);
    }

    @DeleteMapping("/{id}")
    public void deleteTipoEvaluacion(@PathVariable("id") Long id) {
        tipoEvaluacionService.deleteTipoEvaluacion(id);
    }
}

