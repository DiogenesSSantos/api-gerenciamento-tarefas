package com.github.diogenesssantos.facilittecnologia.repository;

import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;

import java.util.List;
import java.util.Optional;

public class TarefaRepositoryImpl implements TarefaRepositoryCustom{



    @Override
    public Optional<Tarefa> buscarPorTitulo(String titulo) {
        //todo implementar buscar por titulo
        return null;
    }

    @Override
    public Optional<Tarefa> buscarPorDescricao(String descricao) {
        //todo implementar buscar por titulo
        return null;
    }

    @Override
    public List<Tarefa> buscarPorStatus(Status status) {
        return List.of();
    }


}
