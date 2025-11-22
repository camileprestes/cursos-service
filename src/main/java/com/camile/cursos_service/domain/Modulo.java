package com.camile.cursos_service.domain;

import com.camile.cursos_service.domain.enums.TipoConteudo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "modulos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Column(nullable = false)
    private Integer ordem;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "text")
    private String conteudo;

    @Enumerated(EnumType.STRING)
    private TipoConteudo tipoConteudo;

    private Boolean obrigatorio = true;
    private Integer xpModulo;

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Material> materiais;
}