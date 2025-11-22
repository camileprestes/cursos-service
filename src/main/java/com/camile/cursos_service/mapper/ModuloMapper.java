package com.camile.cursos_service.mapper;

import com.camile.cursos_service.domain.Modulo;
import com.camile.cursos_service.dto.ModuloResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ModuloMapper {

    public ModuloResponseDTO toDTO(Modulo modulo) {
        if (modulo == null) {
            return null;
        }
        return new ModuloResponseDTO(
                modulo.getId(),
                modulo.getTitulo(),
                modulo.getOrdem(),
                modulo.getConteudo(),
                modulo.getTipoConteudo(),
                modulo.getObrigatorio(),
                modulo.getXpModulo(),
                modulo.getCurso().getId()
        );
    }
}