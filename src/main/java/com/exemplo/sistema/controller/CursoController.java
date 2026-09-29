package com.exemplo.sistema.controller;

import com.exemplo.sistema.model.Curso;
import com.exemplo.sistema.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private CursoService servicoCursos;

    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("cursos", servicoCursos.listarTodos());
        return "curso/lista";
    }

    @GetMapping("/novo")
    public String formularioNovo(Model modelo) {
        modelo.addAttribute("curso", new Curso());
        return "curso/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(Curso curso, RedirectAttributes atributos) {
        try {
            servicoCursos.salvar(curso);
            atributos.addFlashAttribute("sucesso", "Curso salvo com sucesso!");
        } catch (IllegalArgumentException erro) {
            atributos.addFlashAttribute("erro", erro.getMessage());
        }
        return "redirect:/cursos";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model modelo) {
        modelo.addAttribute("curso", servicoCursos.buscarPorId(id));
        return "curso/formulario";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        servicoCursos.excluir(id);
        return "redirect:/cursos";
    }
}
