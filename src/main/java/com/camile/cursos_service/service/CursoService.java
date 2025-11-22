package com.camile.cursos_service.service;

import com.camile.cursos_service.domain.Curso;
import com.camile.cursos_service.domain.enums.NivelDificuldade;
import com.camile.cursos_service.domain.Material;
import com.camile.cursos_service.domain.Modulo;
import com.camile.cursos_service.dto.event.CursoDeletadoEventDTO;
import com.camile.cursos_service.dto.event.CursoEventDTO;
import com.camile.cursos_service.dto.CursoRequestDTO;
import com.camile.cursos_service.dto.CursoResponseDTO;
import com.camile.cursos_service.mapper.CursoMapper;
import com.camile.cursos_service.repository.CategoriaRepository;
import com.camile.cursos_service.repository.CursoRepository;
import com.camile.cursos_service.repository.InstrutorRepository;
import com.camile.cursos_service.repository.specification.CursoSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CategoriaRepository categoriaRepository;
    private final InstrutorRepository instrutorRepository; // Mock
    private final EventPublisherService eventPublisher;
    private final CursoMapper cursoMapper;

    public CursoService(CursoRepository cursoRepository, CategoriaRepository categoriaRepository, InstrutorRepository instrutorRepository, EventPublisherService eventPublisher, CursoMapper cursoMapper) {
        this.cursoRepository = cursoRepository;
        this.categoriaRepository = categoriaRepository;
        this.instrutorRepository = instrutorRepository;
        this.eventPublisher = eventPublisher;
        this.cursoMapper = cursoMapper;
    }

    public CursoResponseDTO criarCurso(CursoRequestDTO cursoDTO) {
        // Lógica de Negócio: Validar se a categoria e o instrutor existem antes de criar o curso.
        var categoria = categoriaRepository.findById(cursoDTO.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria com id " + cursoDTO.categoriaId() + " não encontrada."));

        var instrutor = instrutorRepository.findById(cursoDTO.instrutorId())
                .orElseThrow(() -> new EntityNotFoundException("Instrutor com id " + cursoDTO.instrutorId() + " não encontrado."));

        Curso novoCurso = new Curso();
        novoCurso.setCodigo(cursoDTO.codigo());
        novoCurso.setTitulo(cursoDTO.titulo());
        novoCurso.setDescricao(cursoDTO.descricao());
        novoCurso.setDuracaoEstimada(cursoDTO.duracaoEstimada());
        novoCurso.setXpOferecido(cursoDTO.xpOferecido());
        novoCurso.setNivelDificuldade(cursoDTO.nivelDificuldade());
        novoCurso.setPreRequisitos(cursoDTO.preRequisitos());
        novoCurso.setCategoria(categoria);
        novoCurso.setInstrutor(instrutor);
        novoCurso.setModulos(new ArrayList<>()); // Inicializa com lista vazia

        Curso cursoSalvo = cursoRepository.save(novoCurso);

        // Publica o evento de curso criado
        var event = new CursoEventDTO(
                cursoSalvo.getId(),
                cursoSalvo.getTitulo(),
                cursoSalvo.getXpOferecido(),
                cursoSalvo.getInstrutor().getId(),
                cursoSalvo.getAtivo()
        );
        eventPublisher.publish(event, "curso.criado");
        return cursoMapper.toDTO(cursoSalvo);
    }

    public List<CursoResponseDTO> listarTodos(String categoriaCodigo, Long instrutorId, NivelDificuldade nivel, String palavraChave) {
        var spec = CursoSpecification.comFiltros(categoriaCodigo, instrutorId, nivel, palavraChave);
        return cursoRepository.findAll(spec).stream().map(cursoMapper::toDTO).toList();
    }

    public CursoResponseDTO buscarPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso com id " + id + " não encontrado."));
        return cursoMapper.toDTO(curso);
    }

    public void deletarCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso com id " + id + " não encontrado."));

        cursoRepository.deleteById(id);

        // Publica o evento de curso deletado
        var event = new CursoDeletadoEventDTO(id);
        eventPublisher.publish(event, "curso.deletado");
    }

    public CursoResponseDTO atualizarCurso(Long id, CursoRequestDTO dadosAtualizados) {
        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso com id " + id + " não encontrado."));

        var categoria = categoriaRepository.findById(dadosAtualizados.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria com id " + dadosAtualizados.categoriaId() + " não encontrada."));

        var instrutor = instrutorRepository.findById(dadosAtualizados.instrutorId())
                .orElseThrow(() -> new EntityNotFoundException("Instrutor com id " + dadosAtualizados.instrutorId() + " não encontrado."));

        // Atualiza os campos do curso existente com os novos dados
        cursoExistente.setCodigo(dadosAtualizados.getCodigo());
        cursoExistente.setTitulo(dadosAtualizados.getTitulo());
        cursoExistente.setDescricao(dadosAtualizados.getDescricao());
        cursoExistente.setDuracaoEstimada(dadosAtualizados.getDuracaoEstimada());
        cursoExistente.setXpOferecido(dadosAtualizados.getXpOferecido());
        cursoExistente.setNivelDificuldade(dadosAtualizados.getNivelDificuldade());
        cursoExistente.setPreRequisitos(dadosAtualizados.getPreRequisitos());
        cursoExistente.setCategoria(categoria);
        cursoExistente.setInstrutor(instrutor);

        Curso cursoAtualizado = cursoRepository.save(cursoExistente);

        var event = new CursoEventDTO(
                cursoAtualizado.getId(),
                cursoAtualizado.getTitulo(),
                cursoAtualizado.getXpOferecido(),
                cursoAtualizado.getInstrutor().getId(),
                cursoAtualizado.getAtivo()
        );
        eventPublisher.publish(event, "curso.atualizado");
        return cursoMapper.toDTO(cursoAtualizado);
    }

    public CursoResponseDTO ativarCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso com id " + id + " não encontrado."));
        curso.setAtivo(true);
        Curso cursoSalvo = cursoRepository.save(curso);
        var event = new CursoEventDTO(
                cursoSalvo.getId(),
                cursoSalvo.getTitulo(),
                cursoSalvo.getXpOferecido(),
                cursoSalvo.getInstrutor().getId(),
                cursoSalvo.getAtivo()
        );
        eventPublisher.publish(event, "curso.atualizado");
        return cursoMapper.toDTO(cursoSalvo);
    }

    public CursoResponseDTO desativarCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso com id " + id + " não encontrado."));
        curso.setAtivo(false);
        Curso cursoSalvo = cursoRepository.save(curso);
        var event = new CursoEventDTO(
                cursoSalvo.getId(),
                cursoSalvo.getTitulo(),
                cursoSalvo.getXpOferecido(),
                cursoSalvo.getInstrutor().getId(),
                cursoSalvo.getAtivo()
        );
        eventPublisher.publish(event, "curso.atualizado");
        return cursoMapper.toDTO(cursoSalvo);
    }

    public CursoResponseDTO duplicarCurso(Long id) {
        Curso cursoOriginal = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso com id " + id + " para duplicar não encontrado."));

        Curso cursoCopia = new Curso();

        // Copia as propriedades do curso, ajustando título e código
        cursoCopia.setTitulo(cursoOriginal.getTitulo() + " (Cópia)");
        cursoCopia.setCodigo(cursoOriginal.getCodigo() + "-COPIA-" + System.currentTimeMillis());
        cursoCopia.setDescricao(cursoOriginal.getDescricao());
        cursoCopia.setDuracaoEstimada(cursoOriginal.getDuracaoEstimada());
        cursoCopia.setXpOferecido(cursoOriginal.getXpOferecido());
        cursoCopia.setNivelDificuldade(cursoOriginal.getNivelDificuldade());
        cursoCopia.setPreRequisitos(cursoOriginal.getPreRequisitos());
        cursoCopia.setCategoria(cursoOriginal.getCategoria());
        cursoCopia.setInstrutor(cursoOriginal.getInstrutor());
        cursoCopia.setAtivo(false); // A cópia começa como inativa por padrão

        // Copia os módulos e seus materiais
        List<Modulo> modulosCopiados = cursoOriginal.getModulos().stream().map(moduloOriginal -> {
            Modulo moduloCopia = new Modulo();
            moduloCopia.setTitulo(moduloOriginal.getTitulo());
            moduloCopia.setOrdem(moduloOriginal.getOrdem());
            moduloCopia.setConteudo(moduloOriginal.getConteudo());
            moduloCopia.setTipoConteudo(moduloOriginal.getTipoConteudo());
            moduloCopia.setObrigatorio(moduloOriginal.getObrigatorio());
            moduloCopia.setXpModulo(moduloOriginal.getXpModulo());
            moduloCopia.setCurso(cursoCopia); // Associa ao novo curso

            List<Material> materiaisCopiados = moduloOriginal.getMateriais().stream().map(materialOriginal -> Material.builder().nomeArquivo(materialOriginal.getNomeArquivo()).tipoArquivo(materialOriginal.getTipoArquivo()).urlStorage(materialOriginal.getUrlStorage()).tamanho(materialOriginal.getTamanho()).modulo(moduloCopia) // Associa ao novo módulo
                    .build()).collect(Collectors.toList());
            moduloCopia.setMateriais(materiaisCopiados);
            return moduloCopia;
        }).collect(Collectors.toList());
        cursoCopia.setModulos(modulosCopiados);

        Curso cursoSalvo = cursoRepository.save(cursoCopia);
        var event = new CursoEventDTO(
                cursoSalvo.getId(),
                cursoSalvo.getTitulo(),
                cursoSalvo.getXpOferecido(),
                cursoSalvo.getInstrutor().getId(),
                cursoSalvo.getAtivo()
        );
        eventPublisher.publish(event, "curso.criado");
        return cursoMapper.toDTO(cursoSalvo);
    }
}
