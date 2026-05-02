package com.github.diogenesssantos.facilittecnologia.service;

import com.github.diogenesssantos.facilittecnologia.exceptionhandller.exception.TarefaNotFoundException;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import com.github.diogenesssantos.facilittecnologia.repository.TarefaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    public Tarefa buscarPorTitulo(String titulo) {
        if (titulo == null) throw new IllegalArgumentException("O campo titulo não pode ser nulo");
        return repository.buscarPorTitulo(titulo)
                .orElseThrow(() ->
                        new TarefaNotFoundException(String.format("Tarefa com  o titulo [%s] não existe.", titulo)));

    }

    public Tarefa buscarPorDescricao(String descricao) {
        if (descricao == null) throw new IllegalArgumentException("O campo descrição não pode ser nulo");
        return repository.buscarPorDescricao(descricao)
                .orElseThrow(() ->
                        new TarefaNotFoundException(String.format("Tarefa com o descrição [%s] não existe.", descricao)));

    }

    @Transactional
    public Tarefa atualizarPorTitulo(String titulo, Tarefa tarefaAtualizada) {
        var tarefa = buscarPorTitulo(titulo);
        atualizarCampos(tarefa, tarefaAtualizada);
        return salvar(tarefa);
    }


    private void atualizarCampos(Tarefa tarefaAtual, Tarefa tarefaAtualizada) {
        tarefaAtual.setTitulo(tarefaAtualizada.getTitulo());
        tarefaAtual.setDescricao(tarefaAtualizada.getDescricao());
        tarefaAtual.setResponsavel(tarefaAtualizada.getResponsavel());
        tarefaAtual.setStatus(tarefaAtualizada.getStatus());
        tarefaAtual.setDataAtualizacao(Instant.now());
        tarefaAtual.setDataLimite(tarefaAtualizada.getDataLimite());
    }

}
