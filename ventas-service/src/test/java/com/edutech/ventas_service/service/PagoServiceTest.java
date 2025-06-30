package com.edutech.ventas_service.service;

import com.edutech.ventas_service.model.Pago;
import com.edutech.ventas_service.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoService pagoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Pago pago1 = new Pago();
        Pago pago2 = new Pago();
        when(pagoRepository.findAll()).thenReturn(Arrays.asList(pago1, pago2));

        List<Pago> pagos = pagoService.findAll();

        assertEquals(2, pagos.size());
        verify(pagoRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Pago pago = new Pago();
        pago.setId(1);
        when(pagoRepository.findById(1)).thenReturn(java.util.Optional.of(pago));

        Pago result = pagoService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(pagoRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {
        Pago pago = new Pago();
        when(pagoRepository.save(pago)).thenReturn(pago);

        Pago result = pagoService.save(pago);

        assertNotNull(result);
        verify(pagoRepository, times(1)).save(pago);
    }

    @Test
    void testDelete() {
        doNothing().when(pagoRepository).deleteById(1);

        pagoService.delete(1);

        verify(pagoRepository, times(1)).deleteById(1);
    }
} 