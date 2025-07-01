package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.Curso;
import com.edutech.cursos_inscripciones_service.model.TipoEstadoCurso;
import com.edutech.cursos_inscripciones_service.service.CursoService;
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

@WebMvcTest(CursoController.class)
public class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Curso curso;

    @BeforeEach
    void setUp() {
        curso = new Curso();
        curso.setId(1L);
        curso.setTitulo("Curso de Java");
        curso.setDescripcion("Aprende Java desde cero");
        curso.setFechaCreacion(LocalDate.now());
        curso.setDuracionHoras(40);
        curso.setNumeroOrden(1);
        curso.setEstado(TipoEstadoCurso.ACTIVO);
    }

    @Test
    public void testGetAllCursos() throws Exception {
        when(cursoService.getAllCursos()).thenReturn(List.of(curso));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].titulo").value("Curso de Java"));
    }

    @Test
    public void testGetCursoById() throws Exception {
        when(cursoService.getCursoById(1L)).thenReturn(Optional.of(curso));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cursos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Curso de Java"));
    }

    @Test
    public void testCreateCurso() throws Exception {
        when(cursoService.saveCurso(any(Curso.class))).thenReturn(curso);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(curso)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Curso de Java"));
    }

    @Test
    public void testUpdateCurso() throws Exception {
        when(cursoService.getCursoById(1L)).thenReturn(Optional.of(curso));
        when(cursoService.saveCurso(any(Curso.class))).thenReturn(curso);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cursos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(curso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Curso de Java"));
    }

    @Test
    public void testDeleteCurso() throws Exception {
        when(cursoService.getCursoById(1L)).thenReturn(Optional.of(curso));
        doNothing().when(cursoService).deleteCurso(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cursos/1"))
                .andExpect(status().isNoContent());
        verify(cursoService, times(1)).deleteCurso(1L);
    }
} 