package com.camile.cursos_service.controller;

import com.camile.cursos_service.domain.Instrutor;
import com.camile.cursos_service.repository.InstrutorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instrutores-mock") // Usando "-mock" para deixar claro que é temporário
public class InstrutorController {

    private final InstrutorRepository instrutorRepository;

    public InstrutorController(InstrutorRepository instrutorRepository) {
        this.instrutorRepository = instrutorRepository;
    }

    @PostMapping
    public ResponseEntity<Instrutor> criarInstrutor(@RequestBody Instrutor instrutor) {
        return ResponseEntity.ok(instrutorRepository.save(instrutor));
    }

    @GetMapping
    public ResponseEntity<List<Instrutor>> listarInstrutores() {
        return ResponseEntity.ok(instrutorRepository.findAll());
    }
}