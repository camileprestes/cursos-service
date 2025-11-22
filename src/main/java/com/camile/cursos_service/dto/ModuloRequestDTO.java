package com.camile.cursos_service.dto;

import com.camile.cursos_service.domain.enums.TipoConteudo;

public record ModuloRequestDTO(
        String titulo,
        Integer ordem,
        String conteudo,
        TipoConteudo tipoConteudo,
        Boolean obrigatorio,
        Integer xpModulo
) {
}