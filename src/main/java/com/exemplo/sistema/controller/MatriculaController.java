package com.exemplo.sistema.controller;

import com.exemplo.sistema.model.Matricula;
import com.exemplo.sistema.service.AlunoService;
import com.exemplo.sistema.service.CursoService;
import com.exemplo.sistema.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {
    @Autowired
    private MatriculaService servicoMatriculas;

    @Autowired
    private AlunoService servicoAlunos;

    @Autowired
    private CursoService servicoCursos;

    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("matriculas", servicoMatriculas.listarTodas());
        return "matricula/lista";
    }

    @GetMapping("/novo")
    public String formularioNovo(Model modelo) {
        prepararFormulario(modelo, new Matricula());
        return "matricula/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model modelo) {
        prepararFormulario(modelo, servicoMatriculas.buscarPorId(id));
        return "matricula/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(Matricula matricula, RedirectAttributes atributos) {
        try {
            servicoMatriculas.salvar(matricula);
            atributos.addFlashAttribute("sucesso", "Matrícula salva com sucesso!");
        } catch (IllegalArgumentException erro) {
            atributos.addFlashAttribute("erro", erro.getMessage());
        }
        return "redirect:/matriculas";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            servicoMatriculas.excluir(id);
            atributos.addFlashAttribute("sucesso", "Matrícula excluída com sucesso!");
        } catch (IllegalArgumentException erro) {
            atributos.addFlashAttribute("erro", erro.getMessage());
        }
        return "redirect:/matriculas";
    }

    private void prepararFormulario(Model modelo, Matricula matricula) {
        modelo.addAttribute("matricula", matricula);
        modelo.addAttribute("alunos", servicoAlunos.listarTodos());
        modelo.addAttribute("cursos", servicoCursos.listarTodos());
        modelo.addAttribute("statusList", com.exemplo.sistema.model.StatusMatricula.values());
    }
}
