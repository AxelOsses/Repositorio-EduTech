package com.edutech.cursos_inscripciones_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edutech.cursos_inscripciones_service.model.Inscripcion;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
}