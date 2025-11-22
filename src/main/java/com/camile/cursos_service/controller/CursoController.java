package com.camile.cursos_service.controller;

import com.camile.cursos_service.dto.CursoRequestDTO;
import com.camile.cursos_service.dto.CursoResponseDTO;
import com.camile.cursos_service.domain.enums.NivelDificuldade;
import com.camile.cursos_service.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    // POST /cursos → cadastrar curso
    @PostMapping
    public ResponseEntity<CursoResponseDTO> criarCurso(@Valid @RequestBody CursoRequestDTO cursoDTO) {
        CursoResponseDTO salvo = cursoService.criarCurso(cursoDTO); // Retorna 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // GET /cursos → lista todos
    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> listarCursos(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Long instrutorId,
            @RequestParam(required = false) NivelDificuldade nivel,
            @RequestParam(name = "q", required = false) String palavraChave
    ) {
        return ResponseEntity.ok(cursoService.listarTodos(categoria, instrutorId, nivel, palavraChave));
    }

    // GET /cursos/{id} → busca por ID
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.buscarPorId(id));
    }

    // DELETE /cursos/{id} → remove curso
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCurso(@PathVariable Long id) {
        cursoService.deletarCurso(id);
        return ResponseEntity.noContent().build();
    }

    // PUT /cursos/{id} -> atualiza um curso
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> atualizarCurso(@PathVariable Long id, @Valid @RequestBody CursoRequestDTO cursoDTO) {
        CursoResponseDTO cursoAtualizado = cursoService.atualizarCurso(id, cursoDTO);
        return ResponseEntity.ok(cursoAtualizado);
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<CursoResponseDTO> ativarCurso(@PathVariable Long id) {
        CursoResponseDTO cursoAtualizado = cursoService.ativarCurso(id);
        return ResponseEntity.ok(cursoAtualizado);
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<CursoResponseDTO> desativarCurso(@PathVariable Long id) {
        CursoResponseDTO cursoAtualizado = cursoService.desativarCurso(id);
        return ResponseEntity.ok(cursoAtualizado);
    }

    @PostMapping("/{id}/duplicar")
    public ResponseEntity<CursoResponseDTO> duplicarCurso(@PathVariable Long id) {
        CursoResponseDTO cursoDuplicado = cursoService.duplicarCurso(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDuplicado);
    }
}
