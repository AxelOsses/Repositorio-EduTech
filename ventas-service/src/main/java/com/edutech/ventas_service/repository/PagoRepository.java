package com.edutech.ventas_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.ventas_service.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    // Aquí puedes agregar métodos personalizados si es necesario
    

}
