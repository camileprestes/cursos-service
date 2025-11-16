package com.camile.cursos_service.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CursoDTO(
        @NotBlank String codigo,
        @NotBlank String titulo,
        String descricao,
        @NotNull Long categoriaId,
        @NotNull Long instrutorId,
        Integer duracaoEstimada,
        Integer xpOferecido,
        @NotBlank String nivelDificuldade,
        Boolean ativo,
        String preRequisitos
) {}
