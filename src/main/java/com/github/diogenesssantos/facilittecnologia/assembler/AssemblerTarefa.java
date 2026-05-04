package com.github.diogenesssantos.facilittecnologia.assembler;


import com.github.diogenesssantos.facilittecnologia.controller.request.TarefaRequestDTO;
import com.github.diogenesssantos.facilittecnologia.controller.response.TarefaResponseDTO;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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


    public static Tarefa dtoToModel(TarefaRequestDTO tarefaRequestDTO) {
        return new Tarefa.Builder()
                .titulo(tarefaRequestDTO.titulo())
                .descricao(tarefaRequestDTO.descricao())
                .responsavel(tarefaRequestDTO.responsavel())
                .status(tarefaRequestDTO.status())
                .dataCriacao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .dataAtualizacao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .dataLimite(tarefaRequestDTO.dataLimite().truncatedTo(ChronoUnit.SECONDS))
                .build();

    }
}
