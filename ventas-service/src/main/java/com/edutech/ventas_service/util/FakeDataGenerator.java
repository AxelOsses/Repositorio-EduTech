package com.edutech.ventas_service.util;

import com.edutech.ventas_service.model.Pago;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class FakeDataGenerator {

    private final Faker faker = new Faker();
    private final Random random = new Random();

    // Métodos de pago disponibles
    private static final String[] METODOS_PAGO = {
        "Tarjeta de Crédito", 
        "Tarjeta de Débito", 
        "PayPal", 
        "Transferencia Bancaria", 
        "Efectivo",
        "Apple Pay",
        "Google Pay"
    };

    /**
     * Genera un pago aleatorio
     */
    public Pago generateRandomPago() {
        Pago pago = new Pago();
        pago.setMonto(faker.number().numberBetween(1000, 50000));
        pago.setMetodoPago(METODOS_PAGO[random.nextInt(METODOS_PAGO.length)]);
        
        // Fecha aleatoria en los últimos 12 meses
        LocalDate fechaAleatoria = LocalDate.now().minusDays(random.nextInt(365));
        pago.setFechaPago(Date.valueOf(fechaAleatoria));
        
        return pago;
    }

    /**
     * Genera un pago con parámetros específicos
     */
    public Pago generatePago(Integer monto, String metodoPago, LocalDate fecha) {
        Pago pago = new Pago();
        pago.setMonto(monto != null ? monto : faker.number().numberBetween(1000, 50000));
        pago.setMetodoPago(metodoPago != null ? metodoPago : METODOS_PAGO[random.nextInt(METODOS_PAGO.length)]);
        pago.setFechaPago(Date.valueOf(fecha != null ? fecha : LocalDate.now()));
        
        return pago;
    }

    /**
     * Genera una lista de pagos aleatorios
     */
    public List<Pago> generateRandomPagos(int cantidad) {
        List<Pago> pagos = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            pagos.add(generateRandomPago());
        }
        return pagos;
    }

    /**
     * Genera un monto aleatorio dentro de un rango
     */
    public Integer generateRandomMonto(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

    /**
     * Genera un método de pago aleatorio
     */
    public String generateRandomMetodoPago() {
        return METODOS_PAGO[random.nextInt(METODOS_PAGO.length)];
    }

    /**
     * Genera una fecha aleatoria en el pasado
     */
    public LocalDate generateRandomFechaPasado(int diasMaximos) {
        return LocalDate.now().minusDays(random.nextInt(diasMaximos));
    }

    /**
     * Genera datos de pago para testing con valores específicos
     */
    public Pago generateTestPago() {
        Pago pago = new Pago();
        pago.setMonto(25000);
        pago.setMetodoPago("Tarjeta de Crédito");
        pago.setFechaPago(Date.valueOf(LocalDate.now()));
        
        return pago;
    }
} 