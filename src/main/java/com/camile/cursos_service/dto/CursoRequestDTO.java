package com.camile.cursos_service.dto;

import com.camile.cursos_service.domain.enums.NivelDificuldade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CursoRequestDTO(
        @NotBlank(message = "O código não pode ser vazio.")
        @Size(max = 30, message = "O código deve ter no máximo 30 caracteres.")
        String codigo,

        @NotBlank(message = "O título não pode ser vazio.")
        @Size(max = 200, message = "O título deve ter no máximo 200 caracteres.")
        String titulo,

        String descricao,

        Integer duracaoEstimada,

        @NotNull(message = "O XP oferecido não pode ser nulo.")
        @Positive(message = "O XP oferecido deve ser um número positivo.")
        Integer xpOferecido,

        @NotNull(message = "O nível de dificuldade não pode ser nulo.")
        NivelDificuldade nivelDificuldade,

        String preRequisitos,

        @NotNull(message = "O ID da categoria não pode ser nulo.")
        Long categoriaId,

        @NotNull(message = "O ID do instrutor não pode ser nulo.")
        Long instrutorId
) {
        public String getCodigo() { return codigo; }
        public String getTitulo() { return titulo; }
        public String getDescricao() { return descricao; }
        public Integer getDuracaoEstimada() { return duracaoEstimada; }
        public Integer getXpOferecido() { return xpOferecido; }
        public NivelDificuldade getNivelDificuldade() { return nivelDificuldade; }
        public String getPreRequisitos() { return preRequisitos; }
        public Long getCategoriaId() { return categoriaId; }
        public Long getInstrutorId() { return instrutorId; }
}