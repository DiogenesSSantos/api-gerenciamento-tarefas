package com.github.diogenesssantos.facilittecnologia;

import com.github.diogenesssantos.facilittecnologia.exceptionhandller.exception.BuilderTarefaException;
import com.github.diogenesssantos.facilittecnologia.exceptionhandller.exception.CampoInvalidoException;
import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
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
                dataCriacao(LocalDateTime.now()).
                dataAtualizacao(LocalDateTime.now()).
                dataLimite(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
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
    void CampoInvalidoException_QuandoCriarUmaTarefaComOcampoTituloNullOuVazio() {

        assertAll(() -> {
            assertThrows(CampoInvalidoException.class, () -> {
                new Tarefa.Builder().
                        titulo("     ").
                        descricao("Estágio Facilit").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(LocalDateTime.now()).
                        dataAtualizacao(LocalDateTime.now()).
                        dataLimite(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
                        .build();
            }, () -> "Espereva um campo vázio, mas obteve outro valor.");

            assertThrows(CampoInvalidoException.class, () -> {
                new Tarefa.Builder().
                        titulo(null).
                        descricao("Estágio Facilit").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(LocalDateTime.now()).
                        dataAtualizacao(LocalDateTime.now()).
                        dataLimite(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
                        .build();
            }, () -> "Esperava um campo null, mas obteve outro resultado.");
        });
    }


    @Test
    void CampoInvalidoException_QuandoUmaTarefaComOCampoDescricaoNuloOuVazio() {

        assertAll(() -> {
            assertThrows(CampoInvalidoException.class, () -> {
                new Tarefa.Builder().
                        titulo("Tarefa Facilit").
                        descricao("   ").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(LocalDateTime.now()).
                        dataAtualizacao(LocalDateTime.now()).
                        dataLimite(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
                        .build();
            }, () -> "Espereva um campo vázio, mas obteve outro valor.");

            assertThrows(CampoInvalidoException.class, () -> {
                new Tarefa.Builder().
                        titulo("Tarefa Facilit").
                        descricao(null).
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(LocalDateTime.now()).
                        dataAtualizacao(LocalDateTime.now()).
                        dataLimite(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
                        .build();
            }, () -> "Esperava um campo null, mas obteve outro resultado.");
        });
    }


    @Test
    void CampoInvalidoException_QuandoUmaTarefaComOCampoResponsavelNuloOuVazio() {

        assertAll(() -> {
            assertThrows(CampoInvalidoException.class, () -> {
                new Tarefa.Builder().
                        titulo("Tarefa Facilit").
                        descricao("Estágio Facilit").
                        responsavel("   ").
                        status(Status.FAZER).
                        dataCriacao(LocalDateTime.now()).
                        dataAtualizacao(LocalDateTime.now()).
                        dataLimite(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
                        .build();
            }, () -> "Esperava um campo vázio, mas obteve outro valor.");

            assertThrows(CampoInvalidoException.class, () -> {
                new Tarefa.Builder().
                        titulo("Tarefa Facilit").
                        descricao("Estágio Facilit").
                        responsavel(null).
                        status(Status.FAZER).
                        dataCriacao(LocalDateTime.now()).
                        dataAtualizacao(LocalDateTime.now()).
                        dataLimite(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
                        .build();
            }, () -> "Esperava um campo null, mas obteve outro resultado.");
        });
    }


    @Test
    void CampoInvalidoException_QuandoUmaTarefaComOCampoStatusNulo() {
        assertThrows(CampoInvalidoException.class, () -> {
            new Tarefa.Builder().
                    titulo("Tarefa Facilit").
                    descricao("Estágio facilit").
                    responsavel("Diogenes da Silva Santos").
                    status(null).
                    dataCriacao(LocalDateTime.now()).
                    dataAtualizacao(LocalDateTime.now()).
                    dataLimite(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
                    .build();
        }, () -> "Esperava um campo null, mas obteve outro valor.");
    }


    @Test
    void CampoInvalidoException_QuandoUmaTarefaComOCampoDataCriacaoNulo() {
        assertThrows(CampoInvalidoException.class, () -> {
            new Tarefa.Builder().
                    titulo("Tarefa Facilit").
                    descricao("Estágio facilit").
                    responsavel("Diogenes da Silva Santos").
                    status(Status.FAZER).
                    dataCriacao(null).
                    dataAtualizacao(LocalDateTime.now()).
                    dataLimite(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
                    .build();
        }, () -> "Esperava um campo null, mas obteve outro valor.");
    }


    @Test
    void CampoInvalidoException_QuandoUmaTarefaComOCampoDataAtualizacaoNulo() {
        assertThrows(CampoInvalidoException.class, () -> {
            new Tarefa.Builder().
                    titulo("Tarefa Facilit").
                    descricao("Estágio Facilit").
                    responsavel("Diogenes da Silva Santos").
                    status(Status.FAZER).
                    dataCriacao(LocalDateTime.now()).
                    dataAtualizacao(null).
                    dataLimite(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
                    .build();
        }, () -> "Esperava um campo null, mas obteve outro valor.");
    }


    @Test
    void CampoInvalidoException_QuandoUmaTarefaComOCampoDataLimiteNoPassadoOuNulo() {

        assertAll(() -> {
            assertThrows(CampoInvalidoException.class, () -> {
                new Tarefa.Builder().
                        titulo("Tarefa Facilit").
                        descricao("Estágio facilit").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(LocalDateTime.now()).
                        dataAtualizacao(LocalDateTime.now()).
                        dataLimite(null)
                        .build();
            }, () -> "Esperava um campo null, mas obteve outro valor.");
        }, () -> {
            assertThrows(CampoInvalidoException.class, () -> {
                new Tarefa.Builder().
                        titulo("Tarefa Facilit").
                        descricao("Estágio Facilit").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(LocalDateTime.now()).
                        dataAtualizacao(LocalDateTime.now()).
                        dataLimite(LocalDateTime.now().minus(2, ChronoUnit.MINUTES))
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