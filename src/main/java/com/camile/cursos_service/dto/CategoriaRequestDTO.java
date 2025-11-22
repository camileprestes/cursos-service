package com.camile.cursos_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDTO(
        @NotBlank(message = "O código não pode ser vazio.")
        @Size(max = 30, message = "O código deve ter no máximo 30 caracteres.")
        String codigo,

        @NotBlank(message = "O nome não pode ser vazio.")
        @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres.")
        String nome,

        String descricao,

        @Size(max = 7, message = "A cor hexadecimal deve ter no máximo 7 caracteres.")
        String corHex
) {}