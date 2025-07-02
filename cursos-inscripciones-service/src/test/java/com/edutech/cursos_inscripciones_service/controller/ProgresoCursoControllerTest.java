package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.ProgresoCurso;
import com.edutech.cursos_inscripciones_service.model.Inscripcion;
import com.edutech.cursos_inscripciones_service.service.ProgresoCursoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProgresoCursoController.class)
public class ProgresoCursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProgresoCursoService progresoCursoService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProgresoCurso progresoCurso;

    @BeforeEach
    void setUp() {
        progresoCurso = new ProgresoCurso();
        progresoCurso.setId(1L);
        progresoCurso.setInscripcion(new Inscripcion());
        progresoCurso.setPorcentajeAvance(80);
        progresoCurso.setTiempoTotalEstudio(120L);
    }

    @Test
    public void testFindAll() throws Exception {
        when(progresoCursoService.getAllProgresoCursos()).thenReturn(List.of(progresoCurso));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/progresos-curso"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].porcentajeAvance").value(80));
    }

    @Test
    public void testGetProgresoCursoById() throws Exception {
        when(progresoCursoService.getProgresoCursoById(1L)).thenReturn(Optional.of(progresoCurso));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/progresos-curso/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.porcentajeAvance").value(80));
    }

    @Test
    public void testCreateProgresoCurso() throws Exception {
        when(progresoCursoService.saveProgresoCurso(any(ProgresoCurso.class))).thenReturn(progresoCurso);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/progresos-curso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(progresoCurso)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.porcentajeAvance").value(80));
    }

    @Test
    public void testUpdateProgresoCurso() throws Exception {
        when(progresoCursoService.getProgresoCursoById(1L)).thenReturn(Optional.of(progresoCurso));
        when(progresoCursoService.saveProgresoCurso(any(ProgresoCurso.class))).thenReturn(progresoCurso);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/progresos-curso/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(progresoCurso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.porcentajeAvance").value(80));
    }

    @Test
    public void testDeleteProgresoCurso() throws Exception {
        when(progresoCursoService.getProgresoCursoById(1L)).thenReturn(Optional.of(progresoCurso));
        doNothing().when(progresoCursoService).deleteProgresoCurso(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/progresos-curso/1"))
                .andExpect(status().isNoContent());
        verify(progresoCursoService, times(1)).deleteProgresoCurso(1L);
    }
} 