package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.Modulo;
import com.edutech.cursos_inscripciones_service.service.ModuloService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/modulos")
public class ModuloController {

    private final ModuloService moduloService;

    @Autowired
    public ModuloController(ModuloService moduloService) {
        this.moduloService = moduloService;
    }

    @GetMapping
    public List<Modulo> getAllModulos() {
        return moduloService.getAllModulos();
    }

    @GetMapping("/{id}")
    public Optional<Modulo> getModuloById(@PathVariable("id") Long id) {
        return moduloService.getModuloById(id);
    }

    @PostMapping
    public Modulo createModulo(@RequestBody Modulo modulo) {
        return moduloService.saveModulo(modulo);
    }

    @PutMapping("/{id}")
    public Modulo updateModulo(@PathVariable("id") Long id, @RequestBody Modulo updatedModulo) {
        updatedModulo.setId(id);
        return moduloService.saveModulo(updatedModulo);
    }

    @DeleteMapping("/{id}")
    public void deleteModulo(@PathVariable("id") Long id) {
        moduloService.deleteModulo(id);
    }
}
