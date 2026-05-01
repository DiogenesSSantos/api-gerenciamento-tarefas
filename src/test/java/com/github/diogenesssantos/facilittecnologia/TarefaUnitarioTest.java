package com.github.diogenesssantos.facilittecnologia;

import com.github.diogenesssantos.facilittecnologia.exceptionhandller.exception.BuilderTarefaException;
import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class TarefaUnitarioTest {

    private Tarefa mocktarefa;


    @BeforeEach
    void configuracao() {
        mocktarefa = new Tarefa.Builder().
                titulo("Tarefa teste").
                descricao("Estágio facilit").
                responsavel("Diogenes da Silva Santos").
                status(Status.FAZER).
                dataCriacao(Instant.now()).
                dataAtualizacao(Instant.now()).
                dataLimite(Instant.now().plus(2, ChronoUnit.DAYS))
                .build();

    }


    @Test
    void deveTerTodosOsCamposValidosENaoNulos_QuandoCriarUmaTarefaComBuilder_() {
        Tarefa tarefa = this.mocktarefa;
        assertAll(
                () -> assertNotNull(tarefa.getTitulo()),
                () -> assertNotNull(tarefa.getDescricao()),
                () -> assertNotNull(tarefa.getResponsavel()),
                () -> assertNotNull(tarefa.getStatus()),
                () -> assertNotNull(tarefa.getDataCriacao()),
                () -> assertNotNull(tarefa.getDataAtualizacao()),
                () -> assertNotNull(tarefa.getDataLimite())
        );

    }


    @Test
    void deveLancarIllegalArgumentException_QuandoCriarUmaTarefaComOcampoTituloNullOuVazio() {

        assertAll(() -> {
            assertThrows(IllegalArgumentException.class, () -> {
                new Tarefa.Builder().
                        titulo("     ").
                        descricao("Estágio Facilit").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(2, ChronoUnit.DAYS))
                        .build();
            }, () -> "Espereva um campo vázio, mas obteve outro valor.");

            assertThrows(IllegalArgumentException.class, () -> {
                new Tarefa.Builder().
                        titulo(null).
                        descricao("Estágio Facilit").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(2, ChronoUnit.DAYS))
                        .build();
            }, () -> "Esperava um campo null, mas obteve outro resultado.");
        });
    }


    @Test
    void deveLancarIllegalArgumentException_QuandoUmaTarefaComOCampoDescricaoNuloOuVazio() {

        assertAll(() -> {
            assertThrows(IllegalArgumentException.class, () -> {
                new Tarefa.Builder().
                        titulo("Tarefa Facilit").
                        descricao("   ").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(2, ChronoUnit.DAYS))
                        .build();
            }, () -> "Espereva um campo vázio, mas obteve outro valor.");

            assertThrows(IllegalArgumentException.class, () -> {
                new Tarefa.Builder().
                        titulo("Tarefa Facilit").
                        descricao(null).
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(2, ChronoUnit.DAYS))
                        .build();
            }, () -> "Esperava um campo null, mas obteve outro resultado.");
        });
    }


    @Test
    void deveLancarIllegalArgumentException_QuandoUmaTarefaComOCampoResponsavelNuloOuVazio() {

        assertAll(() -> {
            assertThrows(IllegalArgumentException.class, () -> {
                new Tarefa.Builder().
                        titulo("Tarefa Facilit").
                        descricao("Estágio Facilit").
                        responsavel("   ").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(2, ChronoUnit.DAYS))
                        .build();
            }, () -> "Esperava um campo vázio, mas obteve outro valor.");

            assertThrows(IllegalArgumentException.class, () -> {
                new Tarefa.Builder().
                        titulo("Tarefa Facilit").
                        descricao("Estágio Facilit").
                        responsavel(null).
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(2, ChronoUnit.DAYS))
                        .build();
            }, () -> "Esperava um campo null, mas obteve outro resultado.");
        });
    }


    @Test
    void deveLancarIllegalArgumentException_QuandoUmaTarefaComOCampoStatusNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Tarefa.Builder().
                    titulo("Tarefa Facilit").
                    descricao("Estágio facilit").
                    responsavel("Diogenes da Silva Santos").
                    status(null).
                    dataCriacao(Instant.now()).
                    dataAtualizacao(Instant.now()).
                    dataLimite(Instant.now().plus(2, ChronoUnit.DAYS))
                    .build();
        }, () -> "Esperava um campo null, mas obteve outro valor.");
    }


    @Test
    void deveLancarIllegalArgumentException_QuandoUmaTarefaComOCampoDataCriacaoNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Tarefa.Builder().
                    titulo("Tarefa Facilit").
                    descricao("Estágio facilit").
                    responsavel("Diogenes da Silva Santos").
                    status(Status.FAZER).
                    dataCriacao(null).
                    dataAtualizacao(Instant.now()).
                    dataLimite(Instant.now().plus(2, ChronoUnit.DAYS))
                    .build();
        }, () -> "Esperava um campo null, mas obteve outro valor.");
    }


    @Test
    void deveLancarIllegalArgumentException_QuandoUmaTarefaComOCampoDataAtualizacaoNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Tarefa.Builder().
                    titulo("Tarefa Facilit").
                    descricao("Estágio Facilit").
                    responsavel("Diogenes da Silva Santos").
                    status(Status.FAZER).
                    dataCriacao(Instant.now()).
                    dataAtualizacao(null).
                    dataLimite(Instant.now().plus(2, ChronoUnit.DAYS))
                    .build();
        }, () -> "Esperava um campo null, mas obteve outro valor.");
    }


    @Test
    void deveLancarIllegalArgumentException_QuandoUmaTarefaComOCampoDataLimiteNoPassadoOuNulo() {

        assertAll(() -> {
            assertThrows(IllegalArgumentException.class, () -> {
                new Tarefa.Builder().
                        titulo("Tarefa Facilit").
                        descricao("Estágio facilit").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(null)
                        .build();
            }, () -> "Esperava um campo null, mas obteve outro valor.");
        }, () -> {
            assertThrows(IllegalArgumentException.class, () -> {
                new Tarefa.Builder().
                        titulo("Tarefa Facilit").
                        descricao("Estágio Facilit").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().minus(2, ChronoUnit.MINUTES))
                        .build();
            }, () -> "Esperava um campo dataLimite no passado, mas obteve com valor.");
        });
    }


    @Test
    void deveLancarBuilderTarefaException_QuandoChamaBuildTarefaSemPreencherTodosCamposBuilder() {
        var builderTarefaExceptionExpectativa = "BuilderTarefaException";

        BuilderTarefaException builderTarefaExceptionAtual =
                assertThrows(BuilderTarefaException.class, () -> new Tarefa.Builder().build());


        assertEquals(builderTarefaExceptionExpectativa , builderTarefaExceptionAtual.getClass().getSimpleName());
    }


}