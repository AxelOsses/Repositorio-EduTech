package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.Evaluacion;
import com.edutech.cursos_inscripciones_service.model.Curso;
import com.edutech.cursos_inscripciones_service.model.TipoEvaluacion;
import com.edutech.cursos_inscripciones_service.service.EvaluacionService;
import com.edutech.cursos_inscripciones_service.assemblers.EvaluacionModelAssembler;
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

@WebMvcTest(EvaluacionController.class)
public class EvaluacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvaluacionService evaluacionService;

    @MockBean
    private EvaluacionModelAssembler evaluacionModelAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    private Evaluacion evaluacion;

    @BeforeEach
    void setUp() {
        evaluacion = new Evaluacion();
        evaluacion.setId(1L);
        evaluacion.setNombre("Parcial 1");
        evaluacion.setDescripcion("Primer parcial del curso");
        evaluacion.setCurso(new Curso());
        evaluacion.setTipoEvaluacion(new TipoEvaluacion());
    }

    @Test
    public void testFindAll() throws Exception {
        when(evaluacionService.getAllEvaluaciones()).thenReturn(List.of(evaluacion));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/evaluaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Parcial 1"));
    }

    @Test
    public void testGetEvaluacionById() throws Exception {
        when(evaluacionService.getEvaluacionById(1L)).thenReturn(Optional.of(evaluacion));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/evaluaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Parcial 1"));
    }

    @Test
    public void testCreateEvaluacion() throws Exception {
        when(evaluacionService.saveEvaluacion(any(Evaluacion.class))).thenReturn(evaluacion);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/evaluaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(evaluacion)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Parcial 1"));
    }

    @Test
    public void testUpdateEvaluacion() throws Exception {
        when(evaluacionService.getEvaluacionById(1L)).thenReturn(Optional.of(evaluacion));
        when(evaluacionService.saveEvaluacion(any(Evaluacion.class))).thenReturn(evaluacion);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/evaluaciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(evaluacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Parcial 1"));
    }

    @Test
    public void testDeleteEvaluacion() throws Exception {
        when(evaluacionService.getEvaluacionById(1L)).thenReturn(Optional.of(evaluacion));
        doNothing().when(evaluacionService).deleteEvaluacion(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/evaluaciones/1"))
                .andExpect(status().isNoContent());
        verify(evaluacionService, times(1)).deleteEvaluacion(1L);
    }
} 