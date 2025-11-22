package com.camile.cursos_service.controller;

import com.camile.cursos_service.dto.MaterialResponseDTO;
import com.camile.cursos_service.service.MaterialService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/modulos/{moduloId}/materiais")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MaterialResponseDTO> anexarMaterial(@PathVariable Long moduloId, @RequestParam("arquivo") MultipartFile arquivo) {
        MaterialResponseDTO materialSalvo = materialService.anexarMaterial(moduloId, arquivo);
        return ResponseEntity.ok(materialSalvo);
    }

    @GetMapping
    public ResponseEntity<List<MaterialResponseDTO>> listarMateriais(@PathVariable Long moduloId) {
        return ResponseEntity.ok(materialService.listarMateriaisPorModulo(moduloId));
    }
}