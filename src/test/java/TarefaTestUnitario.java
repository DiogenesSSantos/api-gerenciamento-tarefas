import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TarefaTestUnitario {

    private Tarefa mocktarefa;


    @BeforeEach
    void configuracao() {
        mocktarefa = new Tarefa.Builder().
                titulo("Tarefa teste").
                descricao("Criando tarefa teste").
                responsavel("Diogenes da Silva Santos").
                status(Status.FAZER).
                dataCriacao(Instant.now()).
                dataAtualizacao(Instant.now()).
                dataLimite(Instant.now().plus(2, ChronoUnit.DAYS))
                .build();

    }


    @Test
    void quandoCriarUmaTarefaComBuilder_DeveTerTodosOsCamposValidosENaoNulos() {
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
    void quandoCriarUmaTarefaComOcampoTituloNullOuVazio_DeveLancarIllegalArgumentException() {

        assertAll(() -> {
            assertThrows(IllegalArgumentException.class, () -> {
                new Tarefa.Builder().
                        titulo("     ").
                        descricao("Criando tarefa teste").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(2, ChronoUnit.DAYS))
                        .build();
            }, ()-> "Espereva um campo vázio, mas obteve outro valor.");

            assertThrows(IllegalArgumentException.class, () -> {
                new Tarefa.Builder().
                        titulo(null).
                        descricao("Criando tarefa teste").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(2, ChronoUnit.DAYS))
                        .build();
            }, ()-> "Esperava um campo null, mas obteve outro resultado.");
        });
    }


}