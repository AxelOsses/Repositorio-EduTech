package com.edutech.cursos_inscripciones_service.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.edutech.cursos_inscripciones_service.model.ModuloCursado;

public interface ModuloCursadoRepository extends JpaRepository<ModuloCursado, UUID> {
}

