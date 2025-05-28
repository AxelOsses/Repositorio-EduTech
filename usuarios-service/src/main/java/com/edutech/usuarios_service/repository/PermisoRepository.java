package com.edutech.usuarios_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.usuarios_service.model.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {

}
