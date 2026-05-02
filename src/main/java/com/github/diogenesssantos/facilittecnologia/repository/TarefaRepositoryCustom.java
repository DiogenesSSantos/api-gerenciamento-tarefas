package com.github.diogenesssantos.facilittecnologia.repository;

import com.github.diogenesssantos.facilittecnologia.model.Tarefa;

import java.util.List;

public interface TarefaRepositoryCustom {

    List<Tarefa> buscarPorTitulo(String titulo);
    List<Tarefa> buscarPorDescricao(String descricao);
}
