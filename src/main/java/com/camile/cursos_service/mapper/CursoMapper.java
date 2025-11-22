package com.camile.cursos_service.mapper;

import com.camile.cursos_service.domain.Curso;
import com.camile.cursos_service.dto.CategoriaDTO;
import com.camile.cursos_service.dto.CursoResponseDTO;
import com.camile.cursos_service.dto.InstrutorDTO;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper {

    public CursoResponseDTO toDTO(Curso curso) {
        if (curso == null) {
            return null;
        }
        return new CursoResponseDTO(
                curso.getId(),
                curso.getCodigo(),
                curso.getTitulo(),
                curso.getDescricao(),
                curso.getDuracaoEstimada(),
                curso.getXpOferecido(),
                curso.getNivelDificuldade(),
                curso.getAtivo(),
                curso.getPreRequisitos(),
                new CategoriaDTO(curso.getCategoria().getId(), curso.getCategoria().getNome(), curso.getCategoria().getCodigo()),
                new InstrutorDTO(curso.getInstrutor().getId(), curso.getInstrutor().getNome())
        );
    }
}