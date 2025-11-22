package com.camile.cursos_service.controller;

import com.camile.cursos_service.dto.ModuloRequestDTO;
import com.camile.cursos_service.dto.ModuloResponseDTO;
import com.camile.cursos_service.service.ModuloService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos/{cursoId}/modulos")
public class ModuloController {

    private final ModuloService moduloService;

    public ModuloController(ModuloService moduloService) {
        this.moduloService = moduloService;
    }

    @PostMapping
    public ResponseEntity<ModuloResponseDTO> adicionarModulo(@PathVariable Long cursoId, @RequestBody ModuloRequestDTO moduloDTO) {
        ModuloResponseDTO novoModulo = moduloService.adicionarModuloACurso(cursoId, moduloDTO);
        return ResponseEntity.ok(novoModulo);
    }

    @GetMapping
    public ResponseEntity<List<ModuloResponseDTO>> listarModulosDoCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(moduloService.listarModulosPorCurso(cursoId));
    }

    // O endpoint de delete é mais simples fora do aninhamento
    @DeleteMapping("/modulos/{moduloId}")
    public ResponseEntity<Void> deletarModulo(@PathVariable Long moduloId) {
        moduloService.deletarModulo(moduloId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/modulos/{moduloId}")
    public ResponseEntity<ModuloResponseDTO> atualizarModulo(@PathVariable Long moduloId, @RequestBody ModuloRequestDTO moduloDTO) {
        ModuloResponseDTO moduloAtualizado = moduloService.atualizarModulo(moduloId, moduloDTO);
        return ResponseEntity.ok(moduloAtualizado);
    }

}