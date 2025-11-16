package com.camile.cursos_service.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "instrutor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instrutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;
}