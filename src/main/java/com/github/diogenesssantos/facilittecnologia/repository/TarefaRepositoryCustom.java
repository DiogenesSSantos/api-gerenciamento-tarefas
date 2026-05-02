package com.github.diogenesssantos.facilittecnologia.repository;

import com.github.diogenesssantos.facilittecnologia.model.Tarefa;

import java.util.List;
import java.util.Optional;

public interface TarefaRepositoryCustom {

    Optional<Tarefa> buscarPorTitulo(String titulo);
    Optional<Tarefa> buscarPorDescricao(String descricao);
}
