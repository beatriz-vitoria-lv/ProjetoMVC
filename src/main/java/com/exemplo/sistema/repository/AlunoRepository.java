package com.exemplo.sistema.repository;

import com.exemplo.sistema.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByRg(String rg);
}
