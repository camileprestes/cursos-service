package com.camile.cursos_service.repository;

import com.camile.cursos_service.domain.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long> {
    List<Modulo> findByCursoId(Long cursoId);
}