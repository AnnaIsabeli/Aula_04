package com.biopark.tarefas.service;

import com.biopark.tarefas.model.Tarefa;
import com.biopark.tarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public Tarefa salvar(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> buscarPorId(Long id) {
        return tarefaRepository.findById(id);
    }

    public void excluir(Long id) {
        tarefaRepository.deleteById(id);
    }

    public void alterarStatus(Long id) {
        tarefaRepository.findById(id).ifPresent(tarefa -> {
            tarefa.setConcluida(!tarefa.getConcluida());
            tarefaRepository.save(tarefa);
        });
    }


// Atividade 04: filtrar tarefas por status (pendentes ou concluídas)

    // listar tarefas pendentes
    public List<Tarefa> listarPendentes(){
        List<Tarefa> tarefas = this.listarTodas(); // pega todas as tarefas
        List<Tarefa> pendentes = new ArrayList<>(); // lista que vai guardar apenas as pendentes
        
        for(Tarefa tarefa : tarefas){ // percorre todas as tarefas
            if(!tarefa.getConcluida()){ // verifica se a tarefa NÃO está concluída
                pendentes.add(tarefa); // adiciona na lista de pendentes
            }
        }
        return pendentes;
    }

    // listar tarefas concluídas
    public List<Tarefa> listarConcluidas(){
        List<Tarefa> tarefas = this.listarTodas(); // pega todas as tarefas
        List<Tarefa> concluidas = new ArrayList<>(); // lista para guardar as concluídas

        for(Tarefa tarefa : tarefas){ // percorre todas
            if(tarefa.getConcluida()){ // verifica se está concluída
                concluidas.add(tarefa); // adiciona na lista
            }
        }
        return concluidas;
    }
}
