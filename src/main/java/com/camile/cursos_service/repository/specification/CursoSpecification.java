package com.camile.cursos_service.repository.specification;

import com.camile.cursos_service.domain.Curso;
import com.camile.cursos_service.domain.enums.NivelDificuldade;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CursoSpecification {

    public static Specification<Curso> comFiltros(String categoriaCodigo, Long instrutorId, NivelDificuldade nivel, String palavraChave) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por código da categoria
            if (StringUtils.hasText(categoriaCodigo)) {
                predicates.add(criteriaBuilder.equal(root.get("categoria").get("codigo"), categoriaCodigo));
            }

            // Filtro por ID do instrutor
            if (instrutorId != null) {
                predicates.add(criteriaBuilder.equal(root.get("instrutor").get("id"), instrutorId));
            }

            // Filtro por nível de dificuldade
            if (nivel != null) {
                predicates.add(criteriaBuilder.equal(root.get("nivelDificuldade"), nivel));
            }

            // Filtro por palavra-chave no título ou na descrição
            if (StringUtils.hasText(palavraChave)) {
                Predicate tituloLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")), "%" + palavraChave.toLowerCase() + "%");
                Predicate descricaoLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("descricao")), "%" + palavraChave.toLowerCase() + "%");
                predicates.add(criteriaBuilder.or(tituloLike, descricaoLike));
            }

            // Sempre buscar apenas cursos ativos
            predicates.add(criteriaBuilder.isTrue(root.get("ativo")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}