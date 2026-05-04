package com.github.diogenesssantos.facilittecnologia.assembler;


import com.github.diogenesssantos.facilittecnologia.controller.response.TarefaResponseDTO;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;

import java.util.List;

public class AssemblerTarefa {


    public static TarefaResponseDTO modelToDTO(Tarefa tarefa) {
        return new TarefaResponseDTO(tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getResponsavel(),
                tarefa.getStatus(),
                tarefa.getDataCriacao(),
                tarefa.getDataAtualizacao(),
                tarefa.getDataLimite());

    }



    public static List<TarefaResponseDTO> listModelToListDTO(List<Tarefa> tarefa) {
        return tarefa.stream()
                .map(AssemblerTarefa::modelToDTO)
                .toList();

    }



}
