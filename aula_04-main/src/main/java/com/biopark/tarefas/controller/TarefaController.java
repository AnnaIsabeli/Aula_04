package com.biopark.tarefas.controller;

import com.biopark.tarefas.model.Tarefa;
import com.biopark.tarefas.service.TarefaService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String filtro, Model model){
        List<Tarefa> tarefas;

        // verifica qual filtro foi escolhido
        if("pendentes".equals(filtro)){
            tarefas = tarefaService.listarPendentes();
        }else if("concluidas".equals(filtro)){
            tarefas = tarefaService.listarConcluidas();
        }else{ // se não tiver filtro ou for "todas"
            tarefas = tarefaService.listarTodas();
            filtro = "todas";
        }

        model.addAttribute("tarefas", tarefas); // envia para a view

        model.addAttribute("filtroAtual", filtro); // envia o filtro atual para usar no HTML
        return "lista";
    }   

    @GetMapping("/novo")
    public String novoFormulario(Model model) {
        model.addAttribute("tarefa", new Tarefa());
        return "tarefas/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarFormulario(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return tarefaService.buscarPorId(id)
                .map(tarefa -> {
                    model.addAttribute("tarefa", tarefa);
                    return "tarefas/formulario";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mensagemErro", "Tarefa não encontrada!");
                    return "redirect:/tarefas";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Tarefa tarefa, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "tarefas/formulario";
        }

        boolean isNova = tarefa.getId() == null;

        // Se estiver editando, preservar a data de criação original
        if (!isNova) {
            tarefaService.buscarPorId(tarefa.getId()).ifPresent(existente -> {
                tarefa.setDataCriacao(existente.getDataCriacao());
            });
        }

        tarefaService.salvar(tarefa);

        if (isNova) {
            redirectAttributes.addFlashAttribute("mensagem", "Tarefa criada!");
        } else {
            redirectAttributes.addFlashAttribute("mensagem", "Tarefa atualizada!");
        }

        return "redirect:/tarefas";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        tarefaService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagem", "Tarefa excluída!");
        return "redirect:/tarefas";
    }

    @GetMapping("/status/{id}")
    public String alternarStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        tarefaService.alterarStatus(id);
        redirectAttributes.addFlashAttribute("mensagem", "Status da tarefa alterado!");
        return "redirect:/tarefas";
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String filtro, Model model){
        List<Tarefa> tarefas;

        if("pendentes".equals(filtro)){
            tarefas = tarefaService.listarPendentes();
        }
        else if("concluidas".equals(filtro)){
            tarefas = tarefaService.listarConcluidas();
        }
        else{
            tarefas = tarefaService.listarTodas();
            filtro = "todas";
        }

        // contadores
        int total = tarefaService.listarTodas().size();
        int pendentes = tarefaService.listarPendentes().size();
        int concluidas = tarefaService.listarConcluidas().size();

        model.addAttribute("tarefas", tarefas);
        model.addAttribute("filtroAtual", filtro);

        // envia contadores para a view
        model.addAttribute("total", total);
        model.addAttribute("pendentes", pendentes);
        model.addAttribute("concluidas", concluidas);

        return "lista";
    }
}
