package com.camile.cursos_service.service;

import com.camile.cursos_service.domain.Modulo;
import com.camile.cursos_service.dto.ModuloRequestDTO;
import com.camile.cursos_service.dto.ModuloResponseDTO;
import com.camile.cursos_service.mapper.ModuloMapper;
import com.camile.cursos_service.repository.CursoRepository;
import com.camile.cursos_service.repository.ModuloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuloService {

    private final ModuloRepository moduloRepository;
    private final CursoRepository cursoRepository;
    private final ModuloMapper moduloMapper;

    public ModuloService(ModuloRepository moduloRepository, CursoRepository cursoRepository, ModuloMapper moduloMapper) {
        this.moduloRepository = moduloRepository;
        this.cursoRepository = cursoRepository;
        this.moduloMapper = moduloMapper;
    }

    public ModuloResponseDTO adicionarModuloACurso(Long cursoId, ModuloRequestDTO moduloDTO) {
        // Lógica de Negócio: Busca o curso primeiro para garantir que ele existe.
        var curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso com id " + cursoId + " não encontrado."));

        Modulo novoModulo = new Modulo();
        novoModulo.setTitulo(moduloDTO.titulo());
        novoModulo.setOrdem(moduloDTO.ordem());
        novoModulo.setConteudo(moduloDTO.conteudo());
        novoModulo.setTipoConteudo(moduloDTO.tipoConteudo());
        novoModulo.setObrigatorio(moduloDTO.obrigatorio());
        novoModulo.setXpModulo(moduloDTO.xpModulo());
        // Associa o módulo ao curso encontrado
        novoModulo.setCurso(curso);

        Modulo moduloSalvo = moduloRepository.save(novoModulo);
        return moduloMapper.toDTO(moduloSalvo);
    }

    public List<ModuloResponseDTO> listarModulosPorCurso(Long cursoId) {
        if (!cursoRepository.existsById(cursoId)) {
            throw new EntityNotFoundException("Curso com id " + cursoId + " não encontrado.");
        }
        return moduloRepository.findByCursoId(cursoId).stream()
                .map(moduloMapper::toDTO)
                .toList();
    }

    public void deletarModulo(Long moduloId) {
        if (!moduloRepository.existsById(moduloId)) {
            throw new EntityNotFoundException("Módulo com id " + moduloId + " não encontrado.");
        }
        moduloRepository.deleteById(moduloId);
    }

    public ModuloResponseDTO atualizarModulo(Long moduloId, ModuloRequestDTO dadosAtualizados) {
        Modulo moduloExistente = moduloRepository.findById(moduloId)
                .orElseThrow(() -> new EntityNotFoundException("Módulo com id " + moduloId + " não encontrado."));

        moduloExistente.setOrdem(dadosAtualizados.ordem());
        moduloExistente.setTitulo(dadosAtualizados.titulo());
        moduloExistente.setConteudo(dadosAtualizados.conteudo());
        moduloExistente.setTipoConteudo(dadosAtualizados.tipoConteudo());
        moduloExistente.setObrigatorio(dadosAtualizados.obrigatorio());
        moduloExistente.setXpModulo(dadosAtualizados.xpModulo());

        return moduloMapper.toDTO(moduloRepository.save(moduloExistente));
    }
}
