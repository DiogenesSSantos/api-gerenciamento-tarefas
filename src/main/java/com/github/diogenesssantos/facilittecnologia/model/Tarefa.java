package com.github.diogenesssantos.facilittecnologia.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Getter
@Entity
@Table(schema = "tb_tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String responsavel;

    @Enumerated(EnumType.STRING)
    @Column(name = "t_status")
    private Status status;

    @CreationTimestamp
    private Instant dataCriacao;

    @UpdateTimestamp
    private Instant  dataAtualizacao;

    private Instant  dataLimite;


    private Tarefa(){}


    public  OffsetDateTime getDataCriacao(){
       return dataCriacao.atOffset(ZoneOffset.UTC)
               .atZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
               .toOffsetDateTime();
    }
}
