package com.edutech.cursos_inscripciones_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.edutech.cursos_inscripciones_service.model.Categoria;
import com.edutech.cursos_inscripciones_service.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> getCategoriaById(UUID id) {
        return categoriaRepository.findById(id);
    }

    public Categoria saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deleteCategoria(UUID id) {
        categoriaRepository.deleteById(id);
    }
}

