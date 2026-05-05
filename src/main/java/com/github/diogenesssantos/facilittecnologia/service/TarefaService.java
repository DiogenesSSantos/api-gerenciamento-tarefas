package com.github.diogenesssantos.facilittecnologia.service;

import com.github.diogenesssantos.facilittecnologia.controller.request.TarefaRequestDTO;
import com.github.diogenesssantos.facilittecnologia.exception.TarefaNaoLocalizadaException;
import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import com.github.diogenesssantos.facilittecnologia.repository.TarefaRepository;
import com.github.diogenesssantos.facilittecnologia.util.ValidaHoraUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
                () -> new TarefaNaoLocalizadaException(
                        String.format("A tarefa com o id [%s] não existe no banco de dados.", id), "id"));
    }

    public Tarefa buscarPorTitulo(String titulo) {
        if (titulo == null) throw new IllegalArgumentException("O campo titulo não pode ser nulo");

        return repository.buscarPorTitulo(titulo)
                .orElseThrow(() ->
                        new TarefaNaoLocalizadaException(
                                String.format("A tarefa com o titulo [%s] não existe no banco de dados.", titulo),
                                "titulo"));

    }

    public Tarefa buscarPorDescricao(String descricao) {
        if (descricao == null) throw new IllegalArgumentException("O campo descrição não pode ser nulo");

        return repository.buscarPorDescricao(descricao)
                .orElseThrow(() ->
                        new TarefaNaoLocalizadaException(String.format("A tarefa com o descrição [%s] " +
                                "não existe no banco de dados.", descricao), "descricao"));

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


    @Transactional
    public Tarefa atualizar(Tarefa tarefaBD, TarefaRequestDTO tarefaRequestDTO) {
        if (tarefaRequestDTO.titulo() != null) tarefaBD.setTitulo(tarefaRequestDTO.titulo());
        if (tarefaRequestDTO.descricao() != null) tarefaBD.setDescricao(tarefaRequestDTO.descricao());
        if (tarefaRequestDTO.responsavel() != null) tarefaBD.setResponsavel(tarefaRequestDTO.responsavel());
        if (tarefaRequestDTO.status() != null) tarefaBD.setStatus(tarefaRequestDTO.status());
        if (tarefaRequestDTO.dataLimite() != null) {
            ValidaHoraUtil.futuroOuThrows(tarefaRequestDTO.dataLimite());
            tarefaBD.setDataLimite(tarefaRequestDTO.dataLimite());
        }
        tarefaBD.setDataAtualizacao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        return salvar(tarefaBD);
    }


    @Deprecated
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
