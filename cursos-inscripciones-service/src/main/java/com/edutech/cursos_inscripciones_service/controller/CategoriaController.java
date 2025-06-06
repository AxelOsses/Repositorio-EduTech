package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.Categoria;
import com.edutech.cursos_inscripciones_service.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<Categoria> getAllCategorias() {
        return categoriaService.getAllCategorias();
    }

    @GetMapping("/{id}")
    public Optional<Categoria> getCategoriaById(@PathVariable("id") Long id) {
        return categoriaService.getCategoriaById(id);
    }

    @PostMapping
    public Categoria createCategoria(@RequestBody Categoria categoria) {
        return categoriaService.saveCategoria(categoria);
    }

    @PutMapping("/{id}")
    public Categoria updateCategoria(@PathVariable("id") Long id, @RequestBody Categoria updatedCategoria) {
        updatedCategoria.setId(id);
        return categoriaService.saveCategoria(updatedCategoria);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoria(@PathVariable("id") Long id) {
        categoriaService.deleteCategoria(id);
    }
}

