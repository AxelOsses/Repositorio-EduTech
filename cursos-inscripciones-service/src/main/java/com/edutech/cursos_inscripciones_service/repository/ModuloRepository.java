package com.edutech.cursos_inscripciones_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edutech.cursos_inscripciones_service.model.Modulo;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {
}