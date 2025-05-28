package com.edutech.cursos_inscripciones_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.edutech.cursos_inscripciones_service.model.ProgresoCurso;
import com.edutech.cursos_inscripciones_service.repository.ProgresoCursoRepository;

@Service
public class ProgresoCursoService {

    private final ProgresoCursoRepository progresoCursoRepository;

    public ProgresoCursoService(ProgresoCursoRepository progresoCursoRepository) {
        this.progresoCursoRepository = progresoCursoRepository;
    }

    public List<ProgresoCurso> getAllProgresoCursos() {
        return progresoCursoRepository.findAll();
    }

    public Optional<ProgresoCurso> getProgresoCursoById(Long id) {
        return progresoCursoRepository.findById(id);
    }

    public ProgresoCurso saveProgresoCurso(ProgresoCurso progresoCurso) {
        return progresoCursoRepository.save(progresoCurso);
    }

    public void deleteProgresoCurso(Long id) {
        progresoCursoRepository.deleteById(id);
    }
}

