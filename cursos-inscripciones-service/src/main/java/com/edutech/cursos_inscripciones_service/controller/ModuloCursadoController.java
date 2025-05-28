package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.ModuloCursado;
import com.edutech.cursos_inscripciones_service.service.ModuloCursadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/modulos-cursados")
public class ModuloCursadoController {

    private final ModuloCursadoService moduloCursadoService;

    @Autowired
    public ModuloCursadoController(ModuloCursadoService moduloCursadoService) {
        this.moduloCursadoService = moduloCursadoService;
    }

    @GetMapping
    public List<ModuloCursado> getAllModulosCursados() {
        return moduloCursadoService.getAllModulosCursados();
    }

    @GetMapping("/{id}")
    public Optional<ModuloCursado> getModuloCursadoById(@PathVariable("id") Long id) {
        return moduloCursadoService.getModuloCursadoById(id);
    }

    @PostMapping
    public ModuloCursado createModuloCursado(@RequestBody ModuloCursado moduloCursado) {
        return moduloCursadoService.saveModuloCursado(moduloCursado);
    }

    @PutMapping("/{id}")
    public ModuloCursado updateModuloCursado(@PathVariable("id") Long id, @RequestBody ModuloCursado updatedModuloCursado) {
        updatedModuloCursado.setId(id);
        return moduloCursadoService.saveModuloCursado(updatedModuloCursado);
    }

    @DeleteMapping("/{id}")
    public void deleteModuloCursado(@PathVariable("id") Long id) {
        moduloCursadoService.deleteModuloCursado(id);
    }
}

