package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.Inscripcion;
import com.edutech.cursos_inscripciones_service.service.InscripcionService;
import com.edutech.cursos_inscripciones_service.exception.ResourceNotFoundException;
import com.edutech.cursos_inscripciones_service.exception.ConflictException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @GetMapping
    public ResponseEntity<List<Inscripcion>> getAllInscripciones() {
        List<Inscripcion> inscripciones = inscripcionService.getAllInscripciones();
        return ResponseEntity.ok(inscripciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> getInscripcionById(@PathVariable("id") Long id) {
        return inscripcionService.getInscripcionById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con id: " + id));
    }

    @PostMapping
    public ResponseEntity<Inscripcion> createInscripcion(@Valid @RequestBody Inscripcion inscripcion) {
        if (inscripcion.getId() != null && inscripcionService.getInscripcionById(inscripcion.getId()).isPresent()) {
            throw new ConflictException("Ya existe una inscripción con id: " + inscripcion.getId());
        }
        Inscripcion created = inscripcionService.saveInscripcion(inscripcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> updateInscripcion(@PathVariable("id") Long id, @Valid @RequestBody Inscripcion updatedInscripcion) {
        if (!inscripcionService.getInscripcionById(id).isPresent()) {
            throw new ResourceNotFoundException("Inscripción no encontrada con id: " + id);
        }
        updatedInscripcion.setId(id);
        Inscripcion updated = inscripcionService.saveInscripcion(updatedInscripcion);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInscripcion(@PathVariable("id") Long id) {
        if (!inscripcionService.getInscripcionById(id).isPresent()) {
            throw new ResourceNotFoundException("Inscripción no encontrada con id: " + id);
        }
        inscripcionService.deleteInscripcion(id);
        return ResponseEntity.noContent().build();
    }
}
