package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.InstructorCurso;
import com.edutech.cursos_inscripciones_service.model.Curso;
import com.edutech.cursos_inscripciones_service.service.InstructorCursoService;
import com.edutech.cursos_inscripciones_service.assemblers.InstructorCursoModelAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import java.util.Collections;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InstructorCursoController.class)
public class InstructorCursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InstructorCursoService instructorCursoService;

    @MockBean
    private InstructorCursoModelAssembler instructorCursoModelAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    private InstructorCurso instructorCurso;

    @BeforeEach
    void setUp() {
        instructorCurso = new InstructorCurso();
        instructorCurso.setId(1L);
        instructorCurso.setInstructorId(100L);
        instructorCurso.setCurso(new Curso());
        instructorCurso.setFechaOtorgacion(LocalDate.now());

        // Mock del assembler para evitar NullPointerException
        EntityModel<InstructorCurso> entityModel = EntityModel.of(instructorCurso);
        when(instructorCursoModelAssembler.toModel(any(InstructorCurso.class))).thenReturn(entityModel);
        when(instructorCursoModelAssembler.toCollectionModel(any())).thenReturn(CollectionModel.of(Collections.singletonList(entityModel)));
    }

    @Test
    public void testFindAll() throws Exception {
        when(instructorCursoService.getAllInstructoresCursos()).thenReturn(List.of(instructorCurso));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/instructor-cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].instructorId").value(100L));
    }

    @Test
    public void testGetInstructorCursoById() throws Exception {
        when(instructorCursoService.getInstructorCursoById(1L)).thenReturn(Optional.of(instructorCurso));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/instructor-cursos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.instructorId").value(100L));
    }

    @Test
    public void testCreateInstructorCurso() throws Exception {
        when(instructorCursoService.saveInstructorCurso(any(InstructorCurso.class))).thenReturn(instructorCurso);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/instructor-cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(instructorCurso)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.instructorId").value(100L));
    }

    @Test
    public void testUpdateInstructorCurso() throws Exception {
        when(instructorCursoService.getInstructorCursoById(1L)).thenReturn(Optional.of(instructorCurso));
        when(instructorCursoService.saveInstructorCurso(any(InstructorCurso.class))).thenReturn(instructorCurso);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/instructor-cursos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(instructorCurso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.instructorId").value(100L));
    }

    @Test
    public void testDeleteInstructorCurso() throws Exception {
        when(instructorCursoService.getInstructorCursoById(1L)).thenReturn(Optional.of(instructorCurso));
        doNothing().when(instructorCursoService).deleteInstructorCurso(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/instructor-cursos/1"))
                .andExpect(status().isNoContent());
        verify(instructorCursoService, times(1)).deleteInstructorCurso(1L);
    }
} 