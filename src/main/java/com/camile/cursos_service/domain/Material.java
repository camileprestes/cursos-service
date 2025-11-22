package com.camile.cursos_service.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "materiais")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_id")
    private Modulo modulo;

    @Column(nullable = false)
    private String nomeArquivo;

    @Column(nullable = false, length = 50)
    private String tipoArquivo; // Ex: 'PDF', 'VIDEO_MP4'

    @Column(nullable = false)
    private String urlStorage;

    private Long tamanho; // em bytes
}