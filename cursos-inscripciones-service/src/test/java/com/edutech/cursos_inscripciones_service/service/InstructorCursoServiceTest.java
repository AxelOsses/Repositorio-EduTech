package com.edutech.cursos_inscripciones_service.service;

import com.edutech.cursos_inscripciones_service.model.InstructorCurso;
import com.edutech.cursos_inscripciones_service.repository.InstructorCursoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class InstructorCursoServiceTest {

    @Autowired
    private InstructorCursoService instructorCursoService;

    @MockBean
    private InstructorCursoRepository instructorCursoRepository;

    @Test
    public void testFindAll() {
        InstructorCurso instructorCurso = new InstructorCurso();
        instructorCurso.setId(1L);
        instructorCurso.setInstructorId(100L);
        instructorCurso.setFechaOtorgacion(LocalDate.now());
        when(instructorCursoRepository.findAll()).thenReturn(List.of(instructorCurso));

        List<InstructorCurso> instructores = instructorCursoService.getAllInstructoresCursos();
        assertNotNull(instructores);
        assertEquals(1, instructores.size());
        assertEquals(100L, instructores.get(0).getInstructorId());
    }

    @Test
    public void testGetInstructorCursoById() {
        InstructorCurso instructorCurso = new InstructorCurso();
        instructorCurso.setId(1L);
        when(instructorCursoRepository.findById(1L)).thenReturn(Optional.of(instructorCurso));

        Optional<InstructorCurso> found = instructorCursoService.getInstructorCursoById(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }

    @Test
    public void testSaveInstructorCurso() {
        InstructorCurso instructorCurso = new InstructorCurso();
        instructorCurso.setInstructorId(200L);
        when(instructorCursoRepository.save(instructorCurso)).thenReturn(instructorCurso);

        InstructorCurso saved = instructorCursoService.saveInstructorCurso(instructorCurso);
        assertNotNull(saved);
        assertEquals(200L, saved.getInstructorId());
    }

    @Test
    public void testDeleteInstructorCurso() {
        doNothing().when(instructorCursoRepository).deleteById(1L);
        instructorCursoService.deleteInstructorCurso(1L);
        verify(instructorCursoRepository, times(1)).deleteById(1L);
    }
} 