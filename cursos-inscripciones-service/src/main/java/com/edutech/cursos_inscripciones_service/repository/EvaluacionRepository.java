package com.edutech.cursos_inscripciones_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edutech.cursos_inscripciones_service.model.Evaluacion;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
}
