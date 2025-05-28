package com.edutech.usuarios_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.usuarios_service.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
}
