package com.camile.cursos_service.service;

import com.camile.cursos_service.domain.Categoria;
import com.camile.cursos_service.dto.CategoriaRequestDTO;
import com.camile.cursos_service.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria criarCategoria(CategoriaRequestDTO categoriaDTO) {
        Categoria novaCategoria = new Categoria();
        novaCategoria.setCodigo(categoriaDTO.codigo());
        novaCategoria.setNome(categoriaDTO.nome());
        novaCategoria.setDescricao(categoriaDTO.descricao());
        novaCategoria.setCorHex(categoriaDTO.corHex());

        return categoriaRepository.save(novaCategoria);
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com id " + id + " não encontrada."));
    }

    public void deletarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("Categoria com id " + id + " não encontrada.");
        }
        categoriaRepository.deleteById(id);
    }

    public Categoria atualizarCategoria(Long id, CategoriaRequestDTO dadosAtualizados) {
        Categoria categoriaExistente = this.buscarPorId(id);

        categoriaExistente.setCodigo(dadosAtualizados.codigo());
        categoriaExistente.setNome(dadosAtualizados.nome());
        categoriaExistente.setDescricao(dadosAtualizados.descricao());
        categoriaExistente.setCorHex(dadosAtualizados.corHex());

        return categoriaRepository.save(categoriaExistente);
    }
}
