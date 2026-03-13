package com.example.cadastro_aluno.repository;

import com.example.cadastro_aluno.model.Aluno;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AlunoRepository {
    private List<Aluno> alunos = new ArrayList<>();

    private Long contadorId = 1L;

    // listar todos os alunos
    public List<Aluno> listarTodos(){
        return alunos;
    }

    // salvar aluno
    public void salvar(Aluno aluno){

        // se for novo aluno gera id
        if(aluno.getId() == null){
            aluno.setId(contadorId++);
            alunos.add(aluno);
        }
        else{
            // atualizar aluno
            for(int i = 0; i < alunos.size(); i++){
                if(alunos.get(i).getId().equals(aluno.getId())){
                    alunos.set(i, aluno);
                    break;
                }
            }
        }
    }

    // buscar aluno por id
    public Aluno buscarPorId(Long id){
        for(Aluno a : alunos){
            if(a.getId().equals(id)){
                return a;
            }
        }
        return null;
    }

    // deletar aluno
    public void deletar(Long id){
        alunos.removeIf(a -> a.getId().equals(id));
    }
}
