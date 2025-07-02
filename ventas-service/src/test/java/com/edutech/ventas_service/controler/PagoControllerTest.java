package com.edutech.ventas_service.controler;

import com.edutech.ventas_service.controller.PagoController;
import com.edutech.ventas_service.model.Pago;
import com.edutech.ventas_service.service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagoController.class)
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarPagos_OK() throws Exception {
        Pago pago1 = new Pago(); pago1.setId(1);
        Pago pago2 = new Pago(); pago2.setId(2);
        when(pagoService.findAll()).thenReturn(Arrays.asList(pago1, pago2));

        mockMvc.perform(get("/api/v1/pagos"))
                .andExpect(status().isOk());
    }

    @Test
    void testListarPagos_NoContent() throws Exception {
        when(pagoService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/pagos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testBuscarPago_OK() throws Exception {
        Pago pago = new Pago(); pago.setId(1);
        when(pagoService.findById(1)).thenReturn(pago);
        mockMvc.perform(get("/api/v1/pagos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarPago_NotFound() throws Exception {
        when(pagoService.findById(1)).thenReturn(null);
        mockMvc.perform(get("/api/v1/pagos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGuardarPago_Created() throws Exception {
        Pago pago = new Pago(); pago.setId(1);
        when(pagoService.save(any(Pago.class))).thenReturn(pago);
        mockMvc.perform(post("/api/v1/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isCreated());
    }

    @Test
    void testActualizarPago_OK() throws Exception {
        Pago pago = new Pago(); pago.setId(1);
        when(pagoService.findById(1)).thenReturn(pago);
        when(pagoService.save(any(Pago.class))).thenReturn(pago);
        mockMvc.perform(put("/api/v1/pagos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarPago_NotFound() throws Exception {
        Pago pago = new Pago();
        when(pagoService.findById(1)).thenReturn(null);
        mockMvc.perform(put("/api/v1/pagos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarPago_NoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/pagos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarPago_NotFound() throws Exception {
        org.mockito.Mockito.doThrow(new RuntimeException()).when(pagoService).delete(99);
        mockMvc.perform(delete("/api/v1/pagos/99"))
                .andExpect(status().isNotFound());
    }
} 