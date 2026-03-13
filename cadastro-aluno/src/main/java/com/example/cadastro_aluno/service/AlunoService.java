package com.example.cadastro_aluno.service;

import com.example.cadastro_aluno.model.Aluno;
import com.example.cadastro_aluno.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository repository;

    // injeção de dependência
    public AlunoService(AlunoRepository repository){
        this.repository = repository;
    }

    // listar alunos
    public List<Aluno> listarTodos(){
        return repository.listarTodos();
    }

    // salvar aluno
    public void salvar(Aluno aluno){
        repository.salvar(aluno);
    }

    // buscar por id
    public Aluno buscarPorId(Long id){
        return repository.buscarPorId(id);
    }

    // deletar
    public void deletar(Long id){
        repository.deletar(id);
    }
}