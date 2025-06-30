package com.edutech.ventas_service.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Modelo de Pago")
public class Pago extends RepresentationModel<Pago> {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Schema(description = "ID único del pago", example = "1")
    private Integer id;
    
    @Column(nullable = false)
    @Schema(description = "Monto del pago en centavos", example = "25000", required = true)
    private Integer monto;

    @Column(nullable = false)
    @Schema(description = "Método de pago utilizado", example = "Tarjeta de Crédito", required = true)
    private String metodoPago;

    @Column(nullable = false)
    @Schema(description = "Fecha en que se realizó el pago", example = "2024-01-15", required = true)
    private Date fechaPago;
}
