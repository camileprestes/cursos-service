package com.camile.cursos_service.dto.event;

public record CursoEventDTO(
        Long cursoId,
        String titulo,
        Integer xpOferecido,
        Long instrutorId,
        Boolean ativo
) {
}