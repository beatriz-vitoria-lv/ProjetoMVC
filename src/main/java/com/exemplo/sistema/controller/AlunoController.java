package com.exemplo.sistema.controller;

import com.exemplo.sistema.model.Aluno;
import com.exemplo.sistema.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/alunos")
public class AlunoController {
    @Autowired
    private AlunoService servicoAlunos;

    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("alunos", servicoAlunos.listarTodos());
        return "aluno/lista";
    }

    @GetMapping("/novo")
    public String formularioNovo(Model modelo) {
        modelo.addAttribute("aluno", new Aluno());
        return "aluno/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(Aluno aluno, RedirectAttributes atributos) {
        try {
            servicoAlunos.salvar(aluno);
            atributos.addFlashAttribute("sucesso", "Aluno salvo com sucesso!");
        } catch (IllegalArgumentException erro) {
            atributos.addFlashAttribute("erro", erro.getMessage());
        }
        return "redirect:/alunos";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model modelo) {
        modelo.addAttribute("aluno", servicoAlunos.buscarPorId(id));
        return "aluno/formulario";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        servicoAlunos.excluir(id);
        return "redirect:/alunos";
    }
}
