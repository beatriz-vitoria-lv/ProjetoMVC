package com.exemplo.sistema.repository;

import com.exemplo.sistema.model.Aluno;
import com.exemplo.sistema.model.Curso;
import com.exemplo.sistema.model.Matricula;
import com.exemplo.sistema.model.StatusMatricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    boolean existsByAlunoAndCursoAndStatus(Aluno aluno, Curso curso, StatusMatricula status);

    boolean existsByAluno_IdAndCurso_IdAndStatusAndIdNot(
            Long alunoId, Long cursoId, StatusMatricula status, Long id);

    List<Matricula> findByCursoId(Long cursoId);
}
