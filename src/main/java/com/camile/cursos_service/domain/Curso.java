package com.camile.cursos_service.domain;

import com.camile.cursos_service.domain.enums.NivelDificuldade;
import jakarta.persistence.*;

import java.util.List;
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "instrutor_id")
    private Instrutor instrutor; // Temporário, até o MS Usuários ficar pronto

    private Integer duracaoEstimada; // horas
    private Integer xpOferecido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelDificuldade nivelDificuldade;

    private Boolean ativo = true;

    private String preRequisitos;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Modulo> modulos;
}
