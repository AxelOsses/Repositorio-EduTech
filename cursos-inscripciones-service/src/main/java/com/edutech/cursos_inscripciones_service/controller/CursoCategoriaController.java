package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.CursoCategoria;
import com.edutech.cursos_inscripciones_service.service.CursoCategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/curso-categorias")
public class CursoCategoriaController {

    private final CursoCategoriaService cursoCategoriaService;

    @Autowired
    public CursoCategoriaController(CursoCategoriaService cursoCategoriaService) {
        this.cursoCategoriaService = cursoCategoriaService;
    }

    @GetMapping
    public List<CursoCategoria> getAllCursoCategorias() {
        return cursoCategoriaService.getAllCursoCategorias();
    }

    @GetMapping("/{id}")
    public Optional<CursoCategoria> getCursoCategoriaById(@PathVariable UUID id) {
        return cursoCategoriaService.getCursoCategoriaById(id);
    }

    @PostMapping
    public CursoCategoria createCursoCategoria(@RequestBody CursoCategoria cursoCategoria) {
        return cursoCategoriaService.saveCursoCategoria(cursoCategoria);
    }

    @PutMapping("/{id}")
    public CursoCategoria updateCursoCategoria(@PathVariable UUID id, @RequestBody CursoCategoria updatedCursoCategoria) {
        updatedCursoCategoria.setId(id);
        return cursoCategoriaService.saveCursoCategoria(updatedCursoCategoria);
    }

    @DeleteMapping("/{id}")
    public void deleteCursoCategoria(@PathVariable UUID id) {
        cursoCategoriaService.deleteCursoCategoria(id);
    }
}

