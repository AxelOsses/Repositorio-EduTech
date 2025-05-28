package com.edutech.cursos_inscripciones_service.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.edutech.cursos_inscripciones_service.model.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria, UUID>{

}
