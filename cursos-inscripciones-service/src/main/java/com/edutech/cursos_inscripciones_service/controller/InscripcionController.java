package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.Inscripcion;
import com.edutech.cursos_inscripciones_service.service.InscripcionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @Autowired
    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @GetMapping
    public List<Inscripcion> getAllInscripciones() {
        return inscripcionService.getAllInscripciones();
    }

    @GetMapping("/{id}")
    public Optional<Inscripcion> getInscripcionById(@PathVariable("id") Long id) {
        return inscripcionService.getInscripcionById(id);
    }

    @PostMapping
    public Inscripcion createInscripcion(@RequestBody Inscripcion inscripcion) {
        return inscripcionService.saveInscripcion(inscripcion);
    }

    @PutMapping("/{id}")
    public Inscripcion updateInscripcion(@PathVariable("id") Long id, @RequestBody Inscripcion updatedInscripcion) {
        updatedInscripcion.setId(id);
        return inscripcionService.saveInscripcion(updatedInscripcion);
    }

    @DeleteMapping("/{id}")
    public void deleteInscripcion(@PathVariable("id") Long id) {
        inscripcionService.deleteInscripcion(id);
    }
}
