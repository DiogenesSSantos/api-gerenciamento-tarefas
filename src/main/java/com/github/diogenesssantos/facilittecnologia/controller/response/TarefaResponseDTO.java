package com.github.diogenesssantos.facilittecnologia.controller.response;

import com.github.diogenesssantos.facilittecnologia.model.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

public record TarefaResponseDTO(
        Long id,
        String titulo,
        String descricao,
        String responsavel,
        Status status,
        Instant dataCriacao,
        Instant dataAtualizacao,
        Instant dataLimite) {
}
