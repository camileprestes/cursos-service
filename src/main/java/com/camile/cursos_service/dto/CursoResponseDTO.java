package com.camile.cursos_service.dto;

import com.camile.cursos_service.domain.enums.NivelDificuldade;

public record CursoResponseDTO(
        Long id,
        String codigo,
        String titulo,
        String descricao,
        Integer duracaoEstimada,
        Integer xpOferecido,
        NivelDificuldade nivelDificuldade,
        Boolean ativo,
        String preRequisitos,
        CategoriaDTO categoria,
        InstrutorDTO instrutor
) {
}