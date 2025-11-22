package com.camile.cursos_service.repository;

import com.camile.cursos_service.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findByModuloId(Long moduloId);
}