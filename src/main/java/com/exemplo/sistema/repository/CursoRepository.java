package com.exemplo.sistema.repository;

import com.exemplo.sistema.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
