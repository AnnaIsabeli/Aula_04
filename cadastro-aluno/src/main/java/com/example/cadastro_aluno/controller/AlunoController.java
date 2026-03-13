package com.example.cadastro_aluno.controller;

import com.example.cadastro_aluno.model.Aluno;
import com.example.cadastro_aluno.service.AlunoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alunos")
public class AlunoController {
    private final AlunoService service;
    public AlunoController(AlunoService service){
        this.service = service;
    }

    // listar alunos
    @GetMapping
    public String listar(Model model){
        model.addAttribute("alunos", service.listarTodos());

        return "lista";
    }

    // abrir formulário
    @GetMapping("/novo")
    public String novoAluno(Model model){
        model.addAttribute("aluno", new Aluno());

        return "form";
    }

    // salvar aluno
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Aluno aluno){
        service.salvar(aluno);

        return "redirect:/alunos";
    }

    // editar aluno
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){
        Aluno aluno = service.buscarPorId(id);
        model.addAttribute("aluno", aluno);

        return "form";
    }

    // excluir aluno
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id){
        service.deletar(id);

        return "redirect:/alunos";
    }
}