package com.edutech.cursos_inscripciones_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.edutech.cursos_inscripciones_service.model.Inscripcion;
import com.edutech.cursos_inscripciones_service.repository.InscripcionRepository;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository) {
        this.inscripcionRepository = inscripcionRepository;
    }

    public List<Inscripcion> getAllInscripciones() {
        return inscripcionRepository.findAll();
    }

    public Optional<Inscripcion> getInscripcionById(UUID id) {
        return inscripcionRepository.findById(id);
    }

    public Inscripcion saveInscripcion(Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }

    public void deleteInscripcion(UUID id) {
        inscripcionRepository.deleteById(id);
    }
}

