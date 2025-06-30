package com.edutech.ventas_service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.edutech.ventas_service.controller.PagoController;
import com.edutech.ventas_service.model.Pago;
import com.edutech.ventas_service.service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Date;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(PagoController.class)
class PagoControllerHateoasTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarPagosWithHateoas() throws Exception {
        // Given
        Pago pago1 = new Pago(1, 25000, "Tarjeta de Crédito", Date.valueOf("2024-01-15"));
        Pago pago2 = new Pago(2, 30000, "Transferencia", Date.valueOf("2024-01-16"));
        
        when(pagoService.findAll()).thenReturn(Arrays.asList(pago1, pago2));

        // When & Then
        mockMvc.perform(get("/api/v1/pagos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.pagoList", hasSize(2)))
                .andExpect(jsonPath("$._embedded.pagoList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.pagoList[0].monto").value(25000))
                .andExpect(jsonPath("$._embedded.pagoList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._embedded.pagoList[0]._links.pagos.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testBuscarPagoByIdWithHateoas() throws Exception {
        // Given
        Pago pago = new Pago(1, 25000, "Tarjeta de Crédito", Date.valueOf("2024-01-15"));
        when(pagoService.findById(1)).thenReturn(pago);

        // When & Then
        mockMvc.perform(get("/api/v1/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.monto").value(25000))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.pagos.href").exists())
                .andExpect(jsonPath("$._links.update.href").exists())
                .andExpect(jsonPath("$._links.delete.href").exists());
    }

    @Test
    void testCrearPagoWithHateoas() throws Exception {
        // Given
        Pago pagoToCreate = new Pago(null, 25000, "Tarjeta de Crédito", Date.valueOf("2024-01-15"));
        Pago pagoCreated = new Pago(1, 25000, "Tarjeta de Crédito", Date.valueOf("2024-01-15"));
        
        when(pagoService.save(any(Pago.class))).thenReturn(pagoCreated);

        // When & Then
        mockMvc.perform(post("/api/v1/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pagoToCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.monto").value(25000))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.pagos.href").exists());
    }

    @Test
    void testActualizarPagoWithHateoas() throws Exception {
        // Given
        Pago pagoToUpdate = new Pago(1, 30000, "Transferencia", Date.valueOf("2024-01-16"));
        Pago pagoUpdated = new Pago(1, 30000, "Transferencia", Date.valueOf("2024-01-16"));
        
        when(pagoService.findById(1)).thenReturn(pagoToUpdate);
        when(pagoService.save(any(Pago.class))).thenReturn(pagoUpdated);

        // When & Then
        mockMvc.perform(put("/api/v1/pagos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pagoToUpdate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.monto").value(30000))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.pagos.href").exists())
                .andExpect(jsonPath("$._links.update.href").exists())
                .andExpect(jsonPath("$._links.delete.href").exists());
    }

    @Test
    void testEliminarPago() throws Exception {
        // Given
        when(pagoService.findById(1)).thenReturn(new Pago(1, 25000, "Tarjeta de Crédito", Date.valueOf("2024-01-15")));

        // When & Then
        mockMvc.perform(delete("/api/v1/pagos/1"))
                .andExpect(status().isNoContent());
    }
} 