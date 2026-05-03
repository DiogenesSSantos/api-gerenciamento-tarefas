package com.github.diogenesssantos.facilittecnologia.service;

import com.github.diogenesssantos.facilittecnologia.exceptionhandller.exception.TarefaNotFoundException;
import com.github.diogenesssantos.facilittecnologia.model.Status;
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

    public Tarefa buscarPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("O campo id não pode ser nulo");

        return repository.findById(id).orElseThrow(
                () -> new TarefaNotFoundException(
                        String.format("A tarefa com o id [%s] não existe no banco de dados.", id)));
    }

    public Tarefa buscarPorTitulo(String titulo) {
        if (titulo == null) throw new IllegalArgumentException("O campo titulo não pode ser nulo");

        return repository.buscarPorTitulo(titulo)
                .orElseThrow(() ->
                        new TarefaNotFoundException(
                                String.format("A tarefa com o titulo [%s] não existe no banco de dados.", titulo)));

    }

    public Tarefa buscarPorDescricao(String descricao) {
        if (descricao == null) throw new IllegalArgumentException("O campo descrição não pode ser nulo");

        return repository.buscarPorDescricao(descricao)
                .orElseThrow(() ->
                        new TarefaNotFoundException(String.format("A tarefa com o descrição [%s] " +
                                "não existe no banco de dados.", descricao)));

    }

    public List<Tarefa> buscarPorStatus(Status status) {
        return repository.buscarPorStatus(status);

    }

    @Transactional
    public Tarefa atualizarPorTitulo(String titulo, Tarefa tarefaAtualizada) {
        var tarefa = buscarPorTitulo(titulo);
        atualizar(tarefa, tarefaAtualizada);
        return salvar(tarefa);
    }

    @Transactional
    public Tarefa atualizarPorDescricao(String descricao, Tarefa tarefaAtualizada) {
        var tarefa = buscarPorDescricao(descricao);
        atualizar(tarefa, tarefaAtualizada);
        return salvar(tarefa);
    }

    @Transactional
    public Tarefa AtualizarStatusPorId(Long id, Status statusAtualizado) {
        var tarefaBD = buscarPorId(id);
        atualizar(tarefaBD, statusAtualizado);
        return salvar(tarefaBD);
    }

    @Transactional
    public Tarefa atualizarStatusPorTitulo(String titulo, Status statusExpectativa) {
        var tarefa = buscarPorTitulo(titulo);
        atualizar(tarefa, statusExpectativa);
        return salvar(tarefa);
    }


    private void atualizar(Tarefa tarefaAtual, Tarefa tarefaAtualizada) {
        tarefaAtual.setTitulo(tarefaAtualizada.getTitulo());
        tarefaAtual.setDescricao(tarefaAtualizada.getDescricao());
        tarefaAtual.setResponsavel(tarefaAtualizada.getResponsavel());
        tarefaAtual.setStatus(tarefaAtualizada.getStatus());
        tarefaAtual.setDataAtualizacao(tarefaAtualizada.getDataAtualizacao());
        tarefaAtual.setDataLimite(tarefaAtualizada.getDataLimite());
    }

    private void atualizar(Tarefa tarefaAtual, Status statusAtualizado) {
        tarefaAtual.setStatus(statusAtualizado);
    }

}
