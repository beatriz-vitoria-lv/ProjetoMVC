package com.exemplo.sistema.service;

import com.exemplo.sistema.model.Aluno;
import com.exemplo.sistema.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository repositorioAlunos;

    public void salvar(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do aluno é obrigatório.");
        }
        if (aluno.getEmail() == null || aluno.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("O e-mail do aluno é obrigatório.");
        }
        if (aluno.getRg() == null || aluno.getRg().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo RG é obrigatório.");
        }

        aluno.setRg(aluno.getRg().trim());
        Optional<Aluno> cadastroExistente = repositorioAlunos.findByRg(aluno.getRg());

        if (cadastroExistente.isPresent()
                && (aluno.getId() == null || !cadastroExistente.get().getId().equals(aluno.getId()))) {
            throw new IllegalArgumentException("Já existe um aluno cadastrado com este RG.");
        }

        repositorioAlunos.save(aluno);
    }

    public List<Aluno> listarTodos() {
        return repositorioAlunos.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return repositorioAlunos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));
    }

    public void excluir(Long id) {
        repositorioAlunos.deleteById(id);
    }
}
