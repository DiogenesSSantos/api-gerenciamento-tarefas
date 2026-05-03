package com.github.diogenesssantos.facilittecnologia;


import com.github.diogenesssantos.facilittecnologia.config.TestIntegrationConfig;
import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import com.github.diogenesssantos.facilittecnologia.repository.TarefaRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = TestIntegrationConfig.class)
@TestInstance(PER_CLASS)
public class TarefaRepositoryTest {

    private Tarefa tarefa;

    @Autowired
    private TarefaRepository tarefaRepository;


    @BeforeAll
    void config() {
        tarefa = new Tarefa.Builder().
                titulo("Testando a classe tarefaRepository").
                descricao("Teste a classe tarefaRepository e todos os seus métodos").
                responsavel("Diogenes da Silva Santos").
                status(Status.FAZER).
                dataCriacao(Instant.now()).
                dataAtualizacao(Instant.now()).
                dataLimite(Instant.now().plus(5, ChronoUnit.DAYS))
                .build();
    }


    @Test
    @DisplayName("Deve retornar uma list de Tarefa, quando chamar o método findAll, contendo 20 tarefas.")
    void buscarTodas() {
        var quantidadeExpectativa = 20;

        var listaTarefaBD = tarefaRepository.findAll();

        assertFalse(listaTarefaBD.isEmpty());
        assertEquals(20, listaTarefaBD.size());
    }



    @Test
    @DisplayName("Deve retornar uma Tarefa, quando chamar o método save, e a tarefa tem que conter um ID de número 21.")
    void salvarTarefa() {
        var tarefaExpectatica = tarefa;
        var idExpectativa = 21L;

        var tarefaPersistidaBD = tarefaRepository.save(tarefaExpectatica);

        assertNotNull(tarefaPersistidaBD);
        assertEquals(idExpectativa, tarefaPersistidaBD.getId());
        assertEquals(tarefaExpectatica.getTitulo(), tarefaPersistidaBD.getTitulo());
    }

}
