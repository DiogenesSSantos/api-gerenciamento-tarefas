package com.github.diogenesssantos.facilittecnologia.controller.response;

import com.github.diogenesssantos.facilittecnologia.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;


@Schema(name = "TarefaResponseDTO", description = "DTO de resposta da tarefa.",
        example =
                "{\"id\":\"12\"," +
                        "\"titulo\":\"Facilit desafio estágio\",\"descricao\":\"Melhorar legibilidade do código\"," +
                        "\"responsavel\":\"Diogens da Silva Santos\"," +
                        "\"status\":\"PROGRESSO\",\"dataCriacao\":\"2026-01-15 10:30:00\"," +
                        "\"dataAtualizacao\":\"2026-01-16 11:00:00\"," +
                        " \"dataLimite\":\"2026-02-15 10:30:00\"}")
public record TarefaResponseDTO(
        @Schema(description = "Identificador unico da tarefa", example = "12")
        Long id,
        @Schema(description = "Titulo da tarefa", example = "Facilit desafio estágio")
        String titulo,
        @Schema(description = "Descrição da tarefa", example = "Melhorar legibilidade do código")
        String descricao,
        @Schema(description = "Responsável pela criação da tarefa", example = "Diogens da Silva Santos")
        String responsavel,
        @Schema(description = "Status da tarefa", example = "PROGRESSO")
        Status status,
        @Schema(description = "Data de criação da tarefa ", example = "2026-01-15 10:30:00")
        LocalDateTime dataCriacao,
        @Schema(description = "Data de ultima atualização da tarefa", example = "2026-01-16 11:00:00")
        LocalDateTime dataAtualizacao,
        @Schema(description = "Data de limite para concluir a tarefa", example = "2026-02-15 10:30:00")

        LocalDateTime dataLimite) {
}
