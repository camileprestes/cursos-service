package com.camile.cursos_service.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cursos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String codigo;

    @Column(nullable = false, length = 200)
    private String titulo;

    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(nullable = false)
    private Long instrutorId; // vem do MS Usuários

    private Integer duracaoEstimada; // horas
    private Integer xpOferecido;

    @Column(nullable = false)
    private String nivelDificuldade; // iniciante/intermediário/avançado

    private Boolean ativo = true;

    private String preRequisitos;
}
