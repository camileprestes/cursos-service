package com.camile.cursos_service.service;

import com.camile.cursos_service.domain.Categoria;
import com.camile.cursos_service.domain.Curso;
import com.camile.cursos_service.domain.Instrutor;
import com.camile.cursos_service.domain.enums.NivelDificuldade;
import com.camile.cursos_service.dto.CursoRequestDTO;
import com.camile.cursos_service.dto.CursoResponseDTO;
import com.camile.cursos_service.mapper.CursoMapper;
import com.camile.cursos_service.repository.CategoriaRepository;
import com.camile.cursos_service.repository.CursoRepository;
import com.camile.cursos_service.repository.InstrutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    // Mocks: Versões simuladas das dependências
    @Mock
    private CursoRepository cursoRepository;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private InstrutorRepository instrutorRepository;
    @Mock
    private EventPublisherService eventPublisher;
    @Mock
    private CursoMapper cursoMapper;

    // A classe que queremos testar, com os mocks injetados nela
    @InjectMocks
    private CursoService cursoService;

    private CursoRequestDTO cursoRequestDTO;
    private Categoria categoria;
    private Instrutor instrutor;
    private Curso cursoSalvo;
    private CursoResponseDTO cursoResponseDTO;

    @BeforeEach
    void setUp() {
        // Prepara os objetos que serão usados em múltiplos testes
        categoria = new Categoria(1L, "TECH", "Tecnologia", null, null);
        instrutor = new Instrutor(1L, "Professor Teste");

        cursoRequestDTO = new CursoRequestDTO(
                "CS-01", "Curso de Java", "Aprenda Java", 80, 500,
                NivelDificuldade.INTERMEDIARIO, null, 1L, 1L
        );

        cursoSalvo = new Curso();
        cursoSalvo.setId(1L);
        cursoSalvo.setTitulo("Curso de Java");
        cursoSalvo.setCategoria(categoria);
        cursoSalvo.setInstrutor(instrutor);

        cursoResponseDTO = new CursoResponseDTO(1L, "CS-01", "Curso de Java", null, null, null, null, null, null, null, null);
    }

    @Test
    void criarCurso_ComDadosValidos_DeveRetornarCursoResponseDTO() {
        // Arrange (Organizar): Definimos o comportamento dos mocks
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(instrutorRepository.findById(1L)).thenReturn(Optional.of(instrutor));
        when(cursoRepository.save(any(Curso.class))).thenReturn(cursoSalvo);
        when(cursoMapper.toDTO(cursoSalvo)).thenReturn(cursoResponseDTO);
        doNothing().when(eventPublisher).publish(any(), anyString());

        // Act (Agir): Executamos o método que queremos testar
        CursoResponseDTO resultado = cursoService.criarCurso(cursoRequestDTO);

        // Assert (Verificar): Checamos se o resultado é o esperado
        assertNotNull(resultado);
        assertEquals(cursoResponseDTO.id(), resultado.id());
        assertEquals(cursoResponseDTO.titulo(), resultado.titulo());

    // Verifica se os métodos dos mocks foram chamados
    verify(categoriaRepository, times(1)).findById(1L);
    verify(instrutorRepository, times(1)).findById(1L);
    verify(cursoRepository, times(1)).save(any(Curso.class));
    verify(eventPublisher, times(1)).publish(any(com.camile.cursos_service.dto.event.CursoEventDTO.class), eq("curso.criado"));
    }
}
