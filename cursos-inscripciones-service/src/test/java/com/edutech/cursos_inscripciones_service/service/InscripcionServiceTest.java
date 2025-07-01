package com.edutech.cursos_inscripciones_service.service;

import com.edutech.cursos_inscripciones_service.model.Inscripcion;
import com.edutech.cursos_inscripciones_service.repository.InscripcionRepository;
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
public class InscripcionServiceTest {

    @Autowired
    private InscripcionService inscripcionService;

    @MockBean
    private InscripcionRepository inscripcionRepository;

    @Test
    public void testFindAll() {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(1L);
        inscripcion.setEstudianteId(10L);
        inscripcion.setFechaInscripcion(LocalDate.now());
        inscripcion.setEstaAprobado(true);
        when(inscripcionRepository.findAll()).thenReturn(List.of(inscripcion));

        List<Inscripcion> inscripciones = inscripcionService.getAllInscripciones();
        assertNotNull(inscripciones);
        assertEquals(1, inscripciones.size());
        assertEquals(10L, inscripciones.get(0).getEstudianteId());
    }

    @Test
    public void testGetInscripcionById() {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(1L);
        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(inscripcion));

        Optional<Inscripcion> found = inscripcionService.getInscripcionById(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }

    @Test
    public void testSaveInscripcion() {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudianteId(20L);
        when(inscripcionRepository.save(inscripcion)).thenReturn(inscripcion);

        Inscripcion saved = inscripcionService.saveInscripcion(inscripcion);
        assertNotNull(saved);
        assertEquals(20L, saved.getEstudianteId());
    }

    @Test
    public void testDeleteInscripcion() {
        doNothing().when(inscripcionRepository).deleteById(1L);
        inscripcionService.deleteInscripcion(1L);
        verify(inscripcionRepository, times(1)).deleteById(1L);
    }
} 