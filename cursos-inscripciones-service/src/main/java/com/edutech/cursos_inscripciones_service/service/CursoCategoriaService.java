package com.edutech.cursos_inscripciones_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.edutech.cursos_inscripciones_service.model.CursoCategoria;
import com.edutech.cursos_inscripciones_service.repository.CursoCategoriaRepository;

@Service
public class CursoCategoriaService {

    private final CursoCategoriaRepository cursoCategoriaRepository;

    public CursoCategoriaService(CursoCategoriaRepository cursoCategoriaRepository) {
        this.cursoCategoriaRepository = cursoCategoriaRepository;
    }

    public List<CursoCategoria> getAllCursoCategorias() {
        return cursoCategoriaRepository.findAll();
    }

    public Optional<CursoCategoria> getCursoCategoriaById(Long id) {
        return cursoCategoriaRepository.findById(id);
    }

    public CursoCategoria saveCursoCategoria(CursoCategoria cursoCategoria) {
        return cursoCategoriaRepository.save(cursoCategoria);
    }

    public void deleteCursoCategoria(Long id) {
        cursoCategoriaRepository.deleteById(id);
    }
}
