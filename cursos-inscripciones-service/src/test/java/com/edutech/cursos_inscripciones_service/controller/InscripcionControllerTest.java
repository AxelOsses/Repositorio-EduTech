package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.Inscripcion;
import com.edutech.cursos_inscripciones_service.model.Curso;
import com.edutech.cursos_inscripciones_service.service.InscripcionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InscripcionController.class)
public class InscripcionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InscripcionService inscripcionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Inscripcion inscripcion;

    @BeforeEach
    void setUp() {
        inscripcion = new Inscripcion();
        inscripcion.setId(1L);
        inscripcion.setCurso(new Curso());
        inscripcion.setEstudianteId(10L);
        inscripcion.setFechaInscripcion(LocalDate.now());
        inscripcion.setEstaAprobado(true);
    }

    @Test
    public void testFindAll() throws Exception {
        when(inscripcionService.getAllInscripciones()).thenReturn(List.of(inscripcion));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/inscripciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].estudianteId").value(10L));
    }

    @Test
    public void testGetInscripcionById() throws Exception {
        when(inscripcionService.getInscripcionById(1L)).thenReturn(Optional.of(inscripcion));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/inscripciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.estudianteId").value(10L));
    }

    @Test
    public void testCreateInscripcion() throws Exception {
        when(inscripcionService.saveInscripcion(any(Inscripcion.class))).thenReturn(inscripcion);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/inscripciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inscripcion)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.estudianteId").value(10L));
    }

    @Test
    public void testUpdateInscripcion() throws Exception {
        when(inscripcionService.getInscripcionById(1L)).thenReturn(Optional.of(inscripcion));
        when(inscripcionService.saveInscripcion(any(Inscripcion.class))).thenReturn(inscripcion);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/inscripciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inscripcion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.estudianteId").value(10L));
    }

    @Test
    public void testDeleteInscripcion() throws Exception {
        when(inscripcionService.getInscripcionById(1L)).thenReturn(Optional.of(inscripcion));
        doNothing().when(inscripcionService).deleteInscripcion(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/inscripciones/1"))
                .andExpect(status().isNoContent());
        verify(inscripcionService, times(1)).deleteInscripcion(1L);
    }
} 