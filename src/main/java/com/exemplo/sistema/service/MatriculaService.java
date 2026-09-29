package com.exemplo.sistema.service;

import com.exemplo.sistema.model.Aluno;
import com.exemplo.sistema.model.Curso;
import com.exemplo.sistema.model.Matricula;
import com.exemplo.sistema.model.StatusMatricula;
import com.exemplo.sistema.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatriculaService {
    @Autowired
    private MatriculaRepository repositorioMatriculas;

    @Autowired
    private AlunoService servicoAlunos;

    @Autowired
    private CursoService servicoCursos;

    public void salvar(Matricula matricula) {
        if (matricula.getAluno() == null || matricula.getAluno().getId() == null) {
            throw new IllegalArgumentException("O aluno deve ser selecionado.");
        }
        if (matricula.getCurso() == null || matricula.getCurso().getId() == null) {
            throw new IllegalArgumentException("O curso deve ser selecionado.");
        }

        Aluno alunoCadastrado = servicoAlunos.buscarPorId(matricula.getAluno().getId());
        Curso cursoCadastrado = servicoCursos.buscarPorId(matricula.getCurso().getId());
        matricula.setAluno(alunoCadastrado);
        matricula.setCurso(cursoCadastrado);

        if (matricula.getDataMatricula() == null) {
            matricula.setDataMatricula(LocalDate.now());
        }

        // Toda matrícula nova começa obrigatoriamente como ATIVA.
        if (matricula.getId() == null) {
            matricula.setStatus(StatusMatricula.ATIVA);
        }

        if (matricula.getStatus() == null) {
            throw new IllegalArgumentException("O status da matrícula deve ser informado.");
        }

        if (matricula.getStatus() == StatusMatricula.ATIVA) {
            boolean jaExisteAtiva;

            if (matricula.getId() == null) {
                jaExisteAtiva = repositorioMatriculas.existsByAlunoAndCursoAndStatus(
                        alunoCadastrado, cursoCadastrado, StatusMatricula.ATIVA);
            } else {
                jaExisteAtiva = repositorioMatriculas.existsByAluno_IdAndCurso_IdAndStatusAndIdNot(
                        alunoCadastrado.getId(), cursoCadastrado.getId(),
                        StatusMatricula.ATIVA, matricula.getId());
            }

            if (jaExisteAtiva) {
                throw new IllegalArgumentException("O aluno já possui uma matrícula ativa neste curso.");
            }
        }

        repositorioMatriculas.save(matricula);
    }

    public List<Matricula> listarTodas() {
        return repositorioMatriculas.findAll();
    }

    public Matricula buscarPorId(Long id) {
        return repositorioMatriculas.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada."));
    }

    public void excluir(Long id) {
        if (!repositorioMatriculas.existsById(id)) {
            throw new IllegalArgumentException("Matrícula não encontrada.");
        }
        repositorioMatriculas.deleteById(id);
    }
}
