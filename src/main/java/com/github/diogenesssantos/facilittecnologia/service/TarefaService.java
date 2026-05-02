package com.github.diogenesssantos.facilittecnologia.service;

import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import com.github.diogenesssantos.facilittecnologia.repository.TarefaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    private TarefaRepository repository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.repository = tarefaRepository;
    }

    @Transactional
    public Tarefa salvar(Tarefa tarefa) {
        return repository.save(tarefa);

    }

    public List<Tarefa> buscarTodas() {
        return repository.findAll();
    }

    public List<Tarefa> buscarPorTitulo(String titulo) {
        if (titulo == null) throw new IllegalArgumentException("O titulo não pode ser nulo");
        return repository.buscarPorTitulo(titulo);

    }

    public List<Tarefa> buscarPorDescricao(String descricao) {
        if (descricao == null) throw new IllegalArgumentException("O descricao não pode ser nulo");
        return repository.buscarPorDescricao(descricao);

    }
}
