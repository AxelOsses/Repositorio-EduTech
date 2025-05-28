package com.edutech.cursos_inscripciones_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.edutech.cursos_inscripciones_service.model.NotaEvaluacion;
import com.edutech.cursos_inscripciones_service.repository.NotaEvaluacionRepository;

@Service
public class NotaEvaluacionService {

    private final NotaEvaluacionRepository notaEvaluacionRepository;

    public NotaEvaluacionService(NotaEvaluacionRepository notaEvaluacionRepository) {
        this.notaEvaluacionRepository = notaEvaluacionRepository;
    }

    public List<NotaEvaluacion> getAllNotasEvaluaciones() {
        return notaEvaluacionRepository.findAll();
    }

    public Optional<NotaEvaluacion> getNotaEvaluacionById(Long id) {
        return notaEvaluacionRepository.findById(id);
    }

    public NotaEvaluacion saveNotaEvaluacion(NotaEvaluacion notaEvaluacion) {
        return notaEvaluacionRepository.save(notaEvaluacion);
    }

    public void deleteNotaEvaluacion(Long id) {
        notaEvaluacionRepository.deleteById(id);
    }
}

