package com.edutech.cursos_inscripciones_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.edutech.cursos_inscripciones_service.model.TipoEvaluacion;
import com.edutech.cursos_inscripciones_service.repository.TipoEvaluacionRepository;

@Service
public class TipoEvaluacionService {

    private final TipoEvaluacionRepository tipoEvaluacionRepository;

    public TipoEvaluacionService(TipoEvaluacionRepository tipoEvaluacionRepository) {
        this.tipoEvaluacionRepository = tipoEvaluacionRepository;
    }

    public List<TipoEvaluacion> getAllTipoEvaluaciones() {
        return tipoEvaluacionRepository.findAll();
    }

    public Optional<TipoEvaluacion> getTipoEvaluacionById(Long id) {
        return tipoEvaluacionRepository.findById(id);
    }

    public TipoEvaluacion saveTipoEvaluacion(TipoEvaluacion tipoEvaluacion) {
        return tipoEvaluacionRepository.save(tipoEvaluacion);
    }

    public void deleteTipoEvaluacion(Long id) {
        tipoEvaluacionRepository.deleteById(id);
    }
}

