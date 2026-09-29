package com.exemplo.sistema.service;

import com.exemplo.sistema.model.Curso;
import com.exemplo.sistema.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CursoService {
    @Autowired
    private CursoRepository repositorioCursos;

    public void salvar(Curso curso) {
        if (curso.getNome() == null || curso.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do curso é obrigatório.");
        }
        if (curso.getCargaHoraria() == null || curso.getCargaHoraria() <= 0) {
            throw new IllegalArgumentException("A carga horária deve ser maior que zero.");
        }
        repositorioCursos.save(curso);
    }

    public List<Curso> listarTodos() { return repositorioCursos.findAll(); }
    public Curso buscarPorId(Long id) { return repositorioCursos.findById(id).orElseThrow(() -> new IllegalArgumentException("Curso não encontrado.")); }
    public void excluir(Long id) { repositorioCursos.deleteById(id); }
}
