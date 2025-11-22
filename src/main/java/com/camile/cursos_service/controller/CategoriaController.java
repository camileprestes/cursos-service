package com.camile.cursos_service.controller;

import com.camile.cursos_service.domain.Categoria;
import com.camile.cursos_service.dto.CategoriaDTO;
import com.camile.cursos_service.dto.CategoriaRequestDTO;
import com.camile.cursos_service.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> criarCategoria(@RequestBody CategoriaRequestDTO categoriaDTO) {
        Categoria novaCategoria = categoriaService.criarCategoria(categoriaDTO);
        return ResponseEntity.ok(new CategoriaDTO(novaCategoria.getId(), novaCategoria.getNome(), novaCategoria.getCodigo()));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        List<CategoriaDTO> categorias = categoriaService.listarTodas().stream()
                .map(cat -> new CategoriaDTO(cat.getId(), cat.getNome(), cat.getCodigo()))
                .toList();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(new CategoriaDTO(categoria.getId(), categoria.getNome(), categoria.getCodigo()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        categoriaService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> atualizarCategoria(@PathVariable Long id, @RequestBody CategoriaRequestDTO categoriaDTO) {
        Categoria categoriaAtualizada = categoriaService.atualizarCategoria(id, categoriaDTO);
        return ResponseEntity.ok(new CategoriaDTO(categoriaAtualizada.getId(), categoriaAtualizada.getNome(), categoriaAtualizada.getCodigo()));
    }
}