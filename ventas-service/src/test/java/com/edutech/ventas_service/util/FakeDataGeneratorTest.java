package com.edutech.ventas_service.util;

import com.edutech.ventas_service.model.Pago;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FakeDataGeneratorTest {

    @Autowired
    private FakeDataGenerator fakeDataGenerator;

    @Test
    void testGenerateRandomPago() {
        Pago pago = fakeDataGenerator.generateRandomPago();
        
        assertNotNull(pago);
        assertNotNull(pago.getMonto());
        assertNotNull(pago.getMetodoPago());
        assertNotNull(pago.getFechaPago());
        
        assertTrue(pago.getMonto() >= 1000 && pago.getMonto() <= 50000);
        assertFalse(pago.getMetodoPago().isEmpty());
    }

    @Test
    void testGeneratePagoWithSpecificParameters() {
        Integer monto = 15000;
        String metodoPago = "PayPal";
        LocalDate fecha = LocalDate.now();
        
        Pago pago = fakeDataGenerator.generatePago(monto, metodoPago, fecha);
        
        assertEquals(monto, pago.getMonto());
        assertEquals(metodoPago, pago.getMetodoPago());
        assertEquals(java.sql.Date.valueOf(fecha), pago.getFechaPago());
    }

    @Test
    void testGenerateRandomPagos() {
        int cantidad = 10;
        List<Pago> pagos = fakeDataGenerator.generateRandomPagos(cantidad);
        
        assertEquals(cantidad, pagos.size());
        
        for (Pago pago : pagos) {
            assertNotNull(pago);
            assertNotNull(pago.getMonto());
            assertNotNull(pago.getMetodoPago());
            assertNotNull(pago.getFechaPago());
        }
    }

    @Test
    void testGenerateRandomMonto() {
        int min = 5000;
        int max = 20000;
        
        Integer monto = fakeDataGenerator.generateRandomMonto(min, max);
        
        assertNotNull(monto);
        assertTrue(monto >= min && monto <= max);
    }

    @Test
    void testGenerateRandomMetodoPago() {
        String metodoPago = fakeDataGenerator.generateRandomMetodoPago();
        
        assertNotNull(metodoPago);
        assertFalse(metodoPago.isEmpty());
    }

    @Test
    void testGenerateRandomFechaPasado() {
        int diasMaximos = 30;
        LocalDate fecha = fakeDataGenerator.generateRandomFechaPasado(diasMaximos);
        
        assertNotNull(fecha);
        assertTrue(fecha.isBefore(LocalDate.now()) || fecha.isEqual(LocalDate.now()));
        assertTrue(fecha.isAfter(LocalDate.now().minusDays(diasMaximos)) || fecha.isEqual(LocalDate.now().minusDays(diasMaximos)));
    }

    @Test
    void testGenerateTestPago() {
        Pago pago = fakeDataGenerator.generateTestPago();
        
        assertEquals(25000, pago.getMonto());
        assertEquals("Tarjeta de Crédito", pago.getMetodoPago());
        assertEquals(java.sql.Date.valueOf(LocalDate.now()), pago.getFechaPago());
    }
} 