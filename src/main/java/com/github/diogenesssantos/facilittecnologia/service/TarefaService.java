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
        var listaTarefas = repository.findAll();
        for (Tarefa tarefa : listaTarefas) {
            atualizaStatusTarefaAtrasada(tarefa);
        }

        return listaTarefas;
    }


    public Tarefa buscarPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("O campo id não pode ser nulo");
        var tarefa = repository.findById(id).orElseThrow(
                () -> new TarefaNaoLocalizadaException(
                        String.format("A tarefa com o id [%s] não existe no banco de dados.", id), "id"));
        atualizaStatusTarefaAtrasada(tarefa);

        return tarefa;

    }


    public Tarefa buscarPorTitulo(String titulo) {
        if (titulo == null) throw new IllegalArgumentException("O campo titulo não pode ser nulo");

        var tarefa = repository.buscarPorTitulo(titulo)
                .orElseThrow(() ->
                        new TarefaNaoLocalizadaException(
                                String.format("A tarefa com o titulo [%s] não existe no banco de dados.", titulo),
                                "titulo"));

        atualizaStatusTarefaAtrasada(tarefa);

        return tarefa;

    }


    public Tarefa buscarPorDescricao(String descricao) {
        if (descricao == null) throw new IllegalArgumentException("O campo descrição não pode ser nulo");

        var tarefa = repository.buscarPorDescricao(descricao)
                .orElseThrow(() ->
                        new TarefaNaoLocalizadaException(
                                String.format("A tarefa com o titulo [%s] não existe no banco de dados.", descricao),
                                "titulo"));

        atualizaStatusTarefaAtrasada(tarefa);
        return tarefa;
    }


    public Tarefa buscarPorTituloEDescricao(String titulo, String descricao) {
        if (descricao == null || titulo == null) {
            throw new IllegalArgumentException("O campo descrição não pode ser nulo");
        }

        var tarefa =  repository.buscarPorTituloEDescricao(titulo, descricao)
                .orElseThrow(() ->
                        new TarefaNaoLocalizadaException(String.format("A tarefa associada titulo e descricao " +
                                "não existe no banco de dados.", ""), "titulo e descricao"));

        atualizaStatusTarefaAtrasada(tarefa);

        return tarefa;
    }


    public List<Tarefa> buscarPorStatus(Status status) {
        List<Tarefa> tarefasBD = buscarTodas();

        return tarefasBD.stream()
                .filter(tarefa -> tarefa.getStatus() == status)
                .toList();

    }


    @Transactional
    public Tarefa atualizar(Tarefa tarefaBD, TarefaRequestDTO trDTO) {
        if (trDTO.titulo() != null && !trDTO.titulo().isBlank()) tarefaBD.setTitulo(trDTO.titulo());
        if (trDTO.descricao() != null && !trDTO.descricao().isBlank()) tarefaBD.setDescricao(trDTO.descricao());
        if (trDTO.responsavel() != null && !trDTO.responsavel().isBlank()) tarefaBD.setResponsavel(trDTO.responsavel());
        if (trDTO.status() != null) tarefaBD.setStatus(trDTO.status());
        if (trDTO.dataLimite() != null) {
            ValidaHoraUtil.futuroOuThrows(trDTO.dataLimite());
            tarefaBD.setDataLimite(trDTO.dataLimite());
        }
        tarefaBD.setDataAtualizacao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        return salvar(tarefaBD);
    }


    private static void atualizaStatusTarefaAtrasada(Tarefa tarefa) {
        if (ValidaHoraUtil.isPassado(tarefa.getDataLimite()) && tarefa.getStatus() != Status.CONCLUIDO) {
            tarefa.setStatus(Status.ATRASADO);
        }
    }

}
