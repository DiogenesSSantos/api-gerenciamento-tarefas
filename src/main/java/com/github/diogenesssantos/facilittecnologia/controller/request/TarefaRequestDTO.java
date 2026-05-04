package com.github.diogenesssantos.facilittecnologia.controller.request;

import com.github.diogenesssantos.facilittecnologia.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.time.LocalDateTime;

public record TarefaRequestDTO(
                               @Schema(description = "Titulo da tarefa", example = "Facilit desafio estágio")
                               String titulo,
                               @Schema(description = "Descrição da tarefa", example = "Melhorar legibilidade do código")
                               String descricao,
                               @Schema(description = "Responsável pela criação da tarefa", example = "Diogens da Silva Santos")
                               String responsavel,
                               @Schema(description = "Status da tarefa", example = "PROGRESSO")
                               Status status,
                               @Schema(description = "Data de limite para concluir a tarefa", example = "2026-05-15T10:30:00")
                               LocalDateTime dataLimite)  {
}
