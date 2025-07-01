package com.edutech.cursos_inscripciones_service.service;

import com.edutech.cursos_inscripciones_service.model.Evaluacion;
import com.edutech.cursos_inscripciones_service.repository.EvaluacionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EvaluacionServiceTest {

    @Autowired
    private EvaluacionService evaluacionService;

    @MockBean
    private EvaluacionRepository evaluacionRepository;

    @Test
    public void testFindAll() {
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setId(1L);
        evaluacion.setNombre("Parcial 1");
        when(evaluacionRepository.findAll()).thenReturn(List.of(evaluacion));

        List<Evaluacion> evaluaciones = evaluacionService.getAllEvaluaciones();
        assertNotNull(evaluaciones);
        assertEquals(1, evaluaciones.size());
        assertEquals("Parcial 1", evaluaciones.get(0).getNombre());
    }

    @Test
    public void testGetEvaluacionById() {
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setId(1L);
        when(evaluacionRepository.findById(1L)).thenReturn(Optional.of(evaluacion));

        Optional<Evaluacion> found = evaluacionService.getEvaluacionById(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }

    @Test
    public void testSaveEvaluacion() {
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setNombre("Final");
        when(evaluacionRepository.save(evaluacion)).thenReturn(evaluacion);

        Evaluacion saved = evaluacionService.saveEvaluacion(evaluacion);
        assertNotNull(saved);
        assertEquals("Final", saved.getNombre());
    }

    @Test
    public void testDeleteEvaluacion() {
        doNothing().when(evaluacionRepository).deleteById(1L);
        evaluacionService.deleteEvaluacion(1L);
        verify(evaluacionRepository, times(1)).deleteById(1L);
    }
} 