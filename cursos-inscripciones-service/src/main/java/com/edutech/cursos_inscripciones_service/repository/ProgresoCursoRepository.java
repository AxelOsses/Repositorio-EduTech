package com.edutech.cursos_inscripciones_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edutech.cursos_inscripciones_service.model.ProgresoCurso;

public interface ProgresoCursoRepository extends JpaRepository<ProgresoCurso, Long> {
}