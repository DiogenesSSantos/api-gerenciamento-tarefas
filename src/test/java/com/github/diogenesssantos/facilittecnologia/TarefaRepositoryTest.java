package com.github.diogenesssantos.facilittecnologia;


import com.github.diogenesssantos.facilittecnologia.config.TestIntegrationConfig;
import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import com.github.diogenesssantos.facilittecnologia.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
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
                dataCriacao(LocalDateTime.now()).
                dataAtualizacao(LocalDateTime.now()).
                dataLimite(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
                .build();
    }


    @Test
    @DisplayName("Deve retornar uma list de Tarefa, quando chamar o método findAll.")
    void buscarTodas() {
        var listaTarefaBD = tarefaRepository.findAll();
        assertFalse(listaTarefaBD.isEmpty());
    }


    @Test
    @DisplayName("Deve retornar uma Tarefa, quando chamar o método save, e ao retornar a" +
            " Tarefa tem que conter um ID de número 21.")
    void salvarTarefa() {
        var tarefaExpectatica = tarefa;
        var idExpectativa = 21L;

        var tarefaPersistidaBD = tarefaRepository.save(tarefaExpectatica);

        assertNotNull(tarefaPersistidaBD);
        assertEquals(idExpectativa, tarefaPersistidaBD.getId());
        assertEquals(tarefaExpectatica.getTitulo(), tarefaPersistidaBD.getTitulo());
    }

    @Test
    @DisplayName("Deve retornar um Optional com uma Tarefa, quando chamar o método findById com um id valido.")
    void buscarPorIdValido() {
        var idExpectativa = 3L;

        var optionaltarefaBD = tarefaRepository.findById(idExpectativa);

        assertTrue(optionaltarefaBD.isPresent());
        assertEquals(idExpectativa, optionaltarefaBD.get().getId());
    }


    @Test
    @DisplayName("Deve retornar um Optional vázio, quando chamar o método findById com um id que não existe no " +
            "banco de dados.")
    void buscarPorIdQueNaoExiste() {
        var idExpectativa = 3233L;

        var optionaltarefaBD = tarefaRepository.findById(idExpectativa);

        assertTrue(optionaltarefaBD.isEmpty());
    }


    @Test
    @DisplayName("Deve retornar um Optional com uma Tarefa, quando chamar o método buscarPorTitulo e o titulo" +
            "correlacione a uma tarefa no banco de dados.")
    void buscarPorTitulo() {
        var tituloExpectativa = "Documentação API";

        var optionalTarefaBD = tarefaRepository.buscarPorTitulo(tituloExpectativa);

        assertTrue(optionalTarefaBD.isPresent());
        assertNotNull(optionalTarefaBD.get().getId());
        assertEquals(tituloExpectativa, optionalTarefaBD.get().getTitulo());

    }


    @Test
    @DisplayName("Deve retornar um Optional vázio, quando chamar o método buscarPorTitulo e o titulo" +
            " não correlacionar a uma Tarefa no banco de dados.")
    void buscarPorTituloQueNaoExiste() {
        var tituloExpectativa = "Titulo não existe";

        var optionalTarefaBD = tarefaRepository.buscarPorTitulo(tituloExpectativa);

        assertTrue(optionalTarefaBD.isEmpty());
    }


    @Test
    @DisplayName("Deve retornar um Optional com uma Tarefa, quando chamar o método buscarPorTitulo e o titulo" +
            "correlacione a uma tarefa no banco de dados.")
    void buscarPorDescricao() {
        var descricaoExpectativa = "Harden endpoints e headers";

        var optionalTarefaBD = tarefaRepository.buscarPorDescricao(descricaoExpectativa);

        assertTrue(optionalTarefaBD.isPresent());
        assertNotNull(optionalTarefaBD.get().getId());
        assertEquals(descricaoExpectativa, optionalTarefaBD.get().getDescricao());

    }


    @Test
    @DisplayName("Deve retornar um Optional vázio, quando chamar o método buscarPorTitulo e o titulo" +
            " não correlacionar a uma Tarefa no banco de dados.")
    void buscarPorDescricaoQueNaoExiste() {
        var descricaoExpectativa = "Descrição não existe";

        var optionalTarefaBD = tarefaRepository.buscarPorDescricao(descricaoExpectativa);

        assertTrue(optionalTarefaBD.isEmpty());
    }


    @Test
    @DisplayName("Deve retornar uma list de Tarefa, quando chamar o método buscarPorStatus e o status" +
            " correlacionem a uma ou mais Tarefa no banco de dados.")
    void buscarPorStatus(){
        var statusExpectatica = Status.CONCLUIDO;

        var listTarefaPorStatusBD = tarefaRepository.buscarPorStatus(statusExpectatica);

        assertNotNull(listTarefaPorStatusBD);
        assertTrue(listTarefaPorStatusBD
                .stream()
                .allMatch(tarefaStream -> tarefaStream.getStatus().equals(statusExpectatica) ));
    }


    @Test
    @DisplayName("Deve retornar uma list de Tarefa vázia, quando chamar o método buscarPorStatus e o status" +
            " for ATRASADO.")
    void buscarPorStatusAtrasado_Retornar_Lista_Vazia(){
        var statusExpectatica = Status.ATRASADO;

        var listTarefaPorStatusBD = tarefaRepository.buscarPorStatus(statusExpectatica);

        assertTrue(listTarefaPorStatusBD.isEmpty());
    }

}
