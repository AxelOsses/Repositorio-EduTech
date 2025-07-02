package com.edutech.cursos_inscripciones_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edutech.cursos_inscripciones_service.model.Curso;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long>{
    Optional<Curso> findByTitulo(String titulo);
}


