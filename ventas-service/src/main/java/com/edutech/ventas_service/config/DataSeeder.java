package com.edutech.ventas_service.config;

import com.edutech.ventas_service.model.Pago;
import com.edutech.ventas_service.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private final PagoRepository pagoRepository;

    @Bean
    @Profile("!prod") // Solo se ejecuta en ambientes que no sean producción
    public CommandLineRunner seedData() {
        return args -> {
            log.info("Iniciando seed de datos para ventas-service...");
            
            // Verificar si ya existen datos
            if (pagoRepository.count() > 0) {
                log.info("Ya existen datos en la base de datos. Saltando seed...");
                return;
            }

            Faker faker = new Faker();
            List<Pago> pagos = new ArrayList<>();
            
            // Métodos de pago disponibles
            String[] metodosPago = {"Tarjeta de Crédito", "Tarjeta de Débito", "PayPal", "Transferencia Bancaria", "Efectivo"};
            Random random = new Random();

            // Generar 50 pagos de ejemplo
            for (int i = 0; i < 50; i++) {
                Pago pago = new Pago();
                pago.setMonto(faker.number().numberBetween(1000, 50000)); // Montos entre 1000 y 50000
                pago.setMetodoPago(metodosPago[random.nextInt(metodosPago.length)]);
                
                // Fecha aleatoria en los últimos 6 meses
                LocalDate fechaAleatoria = LocalDate.now().minusDays(random.nextInt(180));
                pago.setFechaPago(Date.valueOf(fechaAleatoria));
                
                pagos.add(pago);
            }

            pagoRepository.saveAll(pagos);
            log.info("Se han generado {} pagos de ejemplo", pagos.size());
        };
    }
} 