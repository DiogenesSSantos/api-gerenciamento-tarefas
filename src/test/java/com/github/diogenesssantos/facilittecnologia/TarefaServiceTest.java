package com.github.diogenesssantos.facilittecnologia;

import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import com.github.diogenesssantos.facilittecnologia.repository.TarefaRepository;
import com.github.diogenesssantos.facilittecnologia.service.TarefaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;


@TestInstance(PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

    @Mock
    private TarefaRepository repository;

    @InjectMocks
    private TarefaService service;

    private List<Tarefa> listTarefas;

    @BeforeAll
    void configuracao() {
        this.listTarefas = List.of(
                new Tarefa.Builder().
                        titulo("Tarefa teste").
                        descricao("Estágio facilit").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(8, ChronoUnit.DAYS))
                        .build(),
                new Tarefa.Builder().
                        titulo("Tarefa teste").
                        descricao("Estágio facilit").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(10, ChronoUnit.DAYS))
                        .build(),
                new Tarefa.Builder().
                        titulo("Tarefa teste").
                        descricao("Estágio facilit").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(22, ChronoUnit.DAYS))
                        .build()
        );

    }


    @Test
    void deveRetornarListaDeTarefas_QuandoChamarOMetodoBuscarTodas_E_Totalizando3Tarefas() {
        given(repository.findAll()).willReturn(listTarefas);
        int expectativaQuantidadeTarefas = 3;

        var listaTarefas = service.buscarTodas();

        then(repository).should().findAll();

        assertNotNull(listaTarefas);
        assertEquals(expectativaQuantidadeTarefas , listaTarefas.size());

    }

    @Test
    void deveRetornarListDeTarefasVazia_QuandoChamarOMetodoBuscarTodas() {


    }



}
