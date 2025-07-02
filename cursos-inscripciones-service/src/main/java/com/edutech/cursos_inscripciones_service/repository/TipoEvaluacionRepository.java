package com.edutech.cursos_inscripciones_service.repository;

import com.edutech.cursos_inscripciones_service.model.TipoEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TipoEvaluacionRepository extends JpaRepository<TipoEvaluacion, Long> {
    Optional<TipoEvaluacion> findByNombre(String nombre);
} 