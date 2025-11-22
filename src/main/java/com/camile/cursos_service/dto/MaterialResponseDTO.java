package com.camile.cursos_service.dto;

public record MaterialResponseDTO(
        Long id,
        String nomeArquivo,
        String tipoArquivo,
        Long tamanho,
        String url
) {
}