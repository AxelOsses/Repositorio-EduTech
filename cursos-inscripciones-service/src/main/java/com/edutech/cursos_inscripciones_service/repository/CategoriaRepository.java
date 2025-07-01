package com.edutech.cursos_inscripciones_service.repository;

import com.edutech.cursos_inscripciones_service.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNombre(String nombre);
} 