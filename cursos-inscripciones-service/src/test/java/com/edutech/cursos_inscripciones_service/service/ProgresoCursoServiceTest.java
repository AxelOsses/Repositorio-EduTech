package com.edutech.cursos_inscripciones_service.service;

import com.edutech.cursos_inscripciones_service.model.ProgresoCurso;
import com.edutech.cursos_inscripciones_service.repository.ProgresoCursoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProgresoCursoServiceTest {

    @Autowired
    private ProgresoCursoService progresoCursoService;

    @MockBean
    private ProgresoCursoRepository progresoCursoRepository;

    @Test
    public void testFindAll() {
        ProgresoCurso progresoCurso = new ProgresoCurso();
        progresoCurso.setId(1L);
        progresoCurso.setPorcentajeAvance(80);
        progresoCurso.setTiempoTotalEstudio(120L);
        when(progresoCursoRepository.findAll()).thenReturn(List.of(progresoCurso));

        List<ProgresoCurso> progresos = progresoCursoService.getAllProgresoCursos();
        assertNotNull(progresos);
        assertEquals(1, progresos.size());
        assertEquals(80, progresos.get(0).getPorcentajeAvance());
    }

    @Test
    public void testGetProgresoCursoById() {
        ProgresoCurso progresoCurso = new ProgresoCurso();
        progresoCurso.setId(1L);
        when(progresoCursoRepository.findById(1L)).thenReturn(Optional.of(progresoCurso));

        Optional<ProgresoCurso> found = progresoCursoService.getProgresoCursoById(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }

    @Test
    public void testSaveProgresoCurso() {
        ProgresoCurso progresoCurso = new ProgresoCurso();
        progresoCurso.setPorcentajeAvance(90);
        when(progresoCursoRepository.save(progresoCurso)).thenReturn(progresoCurso);

        ProgresoCurso saved = progresoCursoService.saveProgresoCurso(progresoCurso);
        assertNotNull(saved);
        assertEquals(90, saved.getPorcentajeAvance());
    }

    @Test
    public void testDeleteProgresoCurso() {
        doNothing().when(progresoCursoRepository).deleteById(1L);
        progresoCursoService.deleteProgresoCurso(1L);
        verify(progresoCursoRepository, times(1)).deleteById(1L);
    }
} 