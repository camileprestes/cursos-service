package com.camile.cursos_service.repository;

import com.camile.cursos_service.domain.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
}