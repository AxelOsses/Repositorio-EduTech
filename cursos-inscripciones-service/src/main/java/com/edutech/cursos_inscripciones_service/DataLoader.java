package com.edutech.cursos_inscripciones_service;

import com.edutech.cursos_inscripciones_service.model.*;
import com.edutech.cursos_inscripciones_service.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private TipoEvaluacionRepository tipoEvaluacionRepository;
    
    @Autowired
    private EvaluacionRepository evaluacionRepository;
    
    @Autowired
    private InscripcionRepository inscripcionRepository;
    
    @Autowired
    private InstructorCursoRepository instructorCursoRepository;
    
    @Autowired
    private ProgresoCursoRepository progresoCursoRepository;
    
    @Autowired
    private ModuloRepository moduloRepository;
    
    @Autowired
    private ModuloCursadoRepository moduloCursadoRepository;
    
    @Autowired
    private NotaEvaluacionRepository notaEvaluacionRepository;
    
    @Autowired
    private CursoCategoriaRepository cursoCategoriaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Limpiar base de datos antes de cargar datos (solo en desarrollo)
        limpiarBaseDeDatos();
        
        Faker faker = new Faker();
        Random random = new Random();

        // Generar categorías
        System.out.println("Generando categorías...");
        for (int i = 0; i < 8; i++) {
            Categoria categoria = new Categoria();
            categoria.setNombre(faker.educator().course() + " " + (i + 1));
            categoriaRepository.save(categoria);
        }

        // Generar tipos de evaluación
        System.out.println("Generando tipos de evaluación...");
        String[] tiposEvaluacion = {"Examen Final", "Quiz", "Proyecto", "Tarea", "Presentación", "Laboratorio"};
        for (String tipo : tiposEvaluacion) {
            TipoEvaluacion te = new TipoEvaluacion();
            te.setNombre(tipo);
            te.setDescripcion(faker.lorem().sentence());
            tipoEvaluacionRepository.save(te);
        }

        // Generar cursos
        System.out.println("Generando cursos...");
        List<Categoria> categorias = categoriaRepository.findAll();
        TipoEstadoCurso[] estados = TipoEstadoCurso.values();
        
        for (int i = 0; i < 15; i++) {
            Curso curso = new Curso();
            curso.setTitulo(faker.educator().course() + " " + (i + 1));
            curso.setDescripcion(faker.lorem().paragraph());
            curso.setFechaCreacion(LocalDate.now().minusDays(random.nextInt(365)));
            curso.setFechaActualizacion(LocalDate.now().minusDays(random.nextInt(30)));
            curso.setDuracionHoras(faker.number().numberBetween(20, 120));
            curso.setNumeroOrden(i + 1);
            curso.setEstado(estados[random.nextInt(estados.length)]);
            
            cursoRepository.save(curso);
        }

        // Generar evaluaciones
        System.out.println("Generando evaluaciones...");
        List<Curso> cursos = cursoRepository.findAll();
        List<TipoEvaluacion> tiposEval = tipoEvaluacionRepository.findAll();
        
        for (int i = 0; i < 30; i++) {
            Evaluacion evaluacion = new Evaluacion();
            evaluacion.setNombre(faker.educator().course() + " - " + faker.options().option("Examen", "Quiz", "Proyecto") + " " + (i + 1));
            evaluacion.setDescripcion(faker.lorem().sentence());
            evaluacion.setCurso(cursos.get(random.nextInt(cursos.size())));
            evaluacion.setTipoEvaluacion(tiposEval.get(random.nextInt(tiposEval.size())));
            
            evaluacionRepository.save(evaluacion);
        }

        // Generar inscripciones
        System.out.println("Generando inscripciones...");
        for (int i = 0; i < 50; i++) {
            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setCurso(cursos.get(random.nextInt(cursos.size())));
            inscripcion.setEstudianteId((long) faker.number().numberBetween(1, 1000));
            inscripcion.setFechaInscripcion(LocalDate.now().minusDays(random.nextInt(180)));
            inscripcion.setEstaAprobado(faker.bool().bool());
            inscripcionRepository.save(inscripcion);
        }

        // Generar instructor-curso
        System.out.println("Generando instructor-curso...");
        for (int i = 0; i < 20; i++) {
            InstructorCurso instructorCurso = new InstructorCurso();
            instructorCurso.setCurso(cursos.get(random.nextInt(cursos.size())));
            instructorCurso.setInstructorId((long) faker.number().numberBetween(1, 100));
            instructorCurso.setFechaOtorgacion(LocalDate.now().minusDays(random.nextInt(365)));
            instructorCursoRepository.save(instructorCurso);
        }

        // Generar progreso de cursos
        System.out.println("Generando progreso de cursos...");
        List<Inscripcion> inscripciones = inscripcionRepository.findAll();
        
        // Crear progreso solo para algunas inscripciones (no todas)
        int numProgresos = Math.min(30, inscripciones.size()); // Máximo 30 progresos
        
        for (int i = 0; i < numProgresos; i++) {
            ProgresoCurso progresoCurso = new ProgresoCurso();
            progresoCurso.setInscripcion(inscripciones.get(i)); // Usar índice secuencial en lugar de aleatorio
            progresoCurso.setPorcentajeAvance(faker.number().numberBetween(0, 100));
            progresoCurso.setTiempoTotalEstudio((long) faker.number().numberBetween(0, 10000)); // en minutos
            progresoCursoRepository.save(progresoCurso);
        }

        // Generar relaciones CursoCategoria explícitamente (sin usar métodos bidireccionales)
        System.out.println("Generando relaciones Curso-Categoría...");
        Set<String> combinaciones = new HashSet<>();
        for (Curso curso : cursos) {
            int numCategorias = random.nextInt(3) + 1; // 1-3 categorías por curso
            for (int j = 0; j < numCategorias; j++) {
                Categoria categoriaAleatoria = categorias.get(random.nextInt(categorias.size()));
                String key = curso.getId() + "-" + categoriaAleatoria.getId();
                if (combinaciones.contains(key)) continue; // Ya existe, saltar
                combinaciones.add(key);

                CursoCategoria cursoCategoria = new CursoCategoria();
                cursoCategoria.setCurso(curso);
                cursoCategoria.setCategoria(categoriaAleatoria);
                cursoCategoriaRepository.save(cursoCategoria);
            }
        }

        // Generar módulos para cada curso
        System.out.println("Generando módulos...");
        for (Curso curso : cursos) {
            int numModulos = random.nextInt(5) + 3; // 3-7 módulos por curso
            for (int i = 0; i < numModulos; i++) {
                Modulo modulo = new Modulo();
                modulo.setTitulo(faker.educator().course() + " - Módulo " + (i + 1));
                modulo.setDescripcion(faker.lorem().paragraph());
                modulo.setNumeroOrden(i + 1);
                modulo.setCursoDirecto(curso); // Usar el método directo para evitar problemas de lazy loading
                moduloRepository.save(modulo);
            }
        }

        // Generar módulos cursados
        System.out.println("Generando módulos cursados...");
        List<Modulo> modulos = moduloRepository.findAll();
        List<ProgresoCurso> progresos = progresoCursoRepository.findAll();
        for (int i = 0; i < 40; i++) {
            ModuloCursado moduloCursado = new ModuloCursado();
            moduloCursado.setProgresoCurso(progresos.get(random.nextInt(progresos.size())));
            moduloCursado.setModulo(modulos.get(random.nextInt(modulos.size())));
            moduloCursado.setEstaAprobado(faker.bool().bool());
            moduloCursado.setFechaAprobacion(LocalDate.now().minusDays(random.nextInt(90)));
            moduloCursadoRepository.save(moduloCursado);
        }

        // Generar notas de evaluación - VERSIÓN CORREGIDA
        System.out.println("Generando notas de evaluación...");
        List<Evaluacion> evaluaciones = evaluacionRepository.findAll();
        List<ModuloCursado> modulosCursados = moduloCursadoRepository.findAll();
        
        // Crear notas de evaluación de forma más controlada
        // Solo crear una nota por módulo cursado para evitar restricciones únicas
        int notasGeneradas = 0;
        int maxNotas = Math.min(20, modulosCursados.size()); // Máximo 20 notas o el número de módulos cursados
        
        for (int i = 0; i < maxNotas; i++) {
            ModuloCursado moduloCursado = modulosCursados.get(i); // Usar índice secuencial
            Evaluacion evaluacion = evaluaciones.get(random.nextInt(evaluaciones.size()));
            
            try {
                NotaEvaluacion notaEvaluacion = new NotaEvaluacion();
                notaEvaluacion.setModuloCursado(moduloCursado);
                notaEvaluacion.setEvaluacion(evaluacion);
                notaEvaluacion.setPuntajeObtenido((float) faker.number().randomDouble(2, 0, 10)); // Puntaje entre 0 y 10
                notaEvaluacion.setPuntajeRequerido((float) faker.number().randomDouble(2, 5, 7)); // Puntaje requerido entre 5 y 7
                notaEvaluacion.setComentario(faker.lorem().sentence());
                notaEvaluacionRepository.save(notaEvaluacion);
                
                notasGeneradas++;
            } catch (Exception e) {
                System.out.println("Error al crear nota de evaluación " + i + ": " + e.getMessage());
                // Continuar con el siguiente
            }
        }
        
        System.out.println("Notas de evaluación generadas: " + notasGeneradas);

        System.out.println("¡Datos de prueba generados exitosamente!");
        System.out.println("Categorías: " + categoriaRepository.count());
        System.out.println("Cursos: " + cursoRepository.count());
        System.out.println("Módulos: " + moduloRepository.count());
        System.out.println("Tipos de evaluación: " + tipoEvaluacionRepository.count());
        System.out.println("Evaluaciones: " + evaluacionRepository.count());
        System.out.println("Inscripciones: " + inscripcionRepository.count());
        System.out.println("Instructor-Curso: " + instructorCursoRepository.count());
        System.out.println("Progreso de cursos: " + progresoCursoRepository.count());
        System.out.println("Módulos cursados: " + moduloCursadoRepository.count());
        System.out.println("Notas de evaluación: " + notaEvaluacionRepository.count());
        System.out.println("Relaciones Curso-Categoría: " + cursoCategoriaRepository.count());
    }
    
    private void limpiarBaseDeDatos() {
        System.out.println("Limpiando base de datos...");
        notaEvaluacionRepository.deleteAll();
        moduloCursadoRepository.deleteAll();
        progresoCursoRepository.deleteAll();
        instructorCursoRepository.deleteAll();
        inscripcionRepository.deleteAll();
        evaluacionRepository.deleteAll();
        moduloRepository.deleteAll();
        cursoCategoriaRepository.deleteAll();
        cursoRepository.deleteAll();
        tipoEvaluacionRepository.deleteAll();
        categoriaRepository.deleteAll();
        System.out.println("Base de datos limpiada.");
    }
} 