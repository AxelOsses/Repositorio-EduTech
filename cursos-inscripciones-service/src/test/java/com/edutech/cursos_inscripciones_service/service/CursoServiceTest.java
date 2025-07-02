package com.edutech.cursos_inscripciones_service.service;

import com.edutech.cursos_inscripciones_service.model.Curso;
import com.edutech.cursos_inscripciones_service.model.TipoEstadoCurso;
import com.edutech.cursos_inscripciones_service.repository.CursoRepository;
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
public class CursoServiceTest {

    @Autowired
    private CursoService cursoService;

    @MockBean
    private CursoRepository cursoRepository;

    @Test
    public void testFindAll() {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setTitulo("Curso de Java");
        curso.setDescripcion("Aprende Java desde cero");
        curso.setFechaCreacion(LocalDate.now());
        curso.setDuracionHoras(40);
        curso.setNumeroOrden(1);
        curso.setEstado(TipoEstadoCurso.ACTIVO);
        when(cursoRepository.findAll()).thenReturn(List.of(curso));

        List<Curso> cursos = cursoService.getAllCursos();
        assertNotNull(cursos);
        assertEquals(1, cursos.size());
        assertEquals("Curso de Java", cursos.get(0).getTitulo());
    }

    @Test
    public void testGetCursoById() {
        Curso curso = new Curso();
        curso.setId(1L);
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        Optional<Curso> found = cursoService.getCursoById(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }

    @Test
    public void testSaveCurso() {
        Curso curso = new Curso();
        curso.setTitulo("Curso de Spring");
        when(cursoRepository.save(curso)).thenReturn(curso);

        Curso saved = cursoService.saveCurso(curso);
        assertNotNull(saved);
        assertEquals("Curso de Spring", saved.getTitulo());
    }

    @Test
    public void testDeleteCurso() {
        doNothing().when(cursoRepository).deleteById(1L);
        cursoService.deleteCurso(1L);
        verify(cursoRepository, times(1)).deleteById(1L);
    }
} 