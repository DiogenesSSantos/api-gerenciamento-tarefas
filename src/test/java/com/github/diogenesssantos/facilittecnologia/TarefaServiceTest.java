package com.github.diogenesssantos.facilittecnologia;

import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import com.github.diogenesssantos.facilittecnologia.repository.TarefaRepository;
import com.github.diogenesssantos.facilittecnologia.service.TarefaService;
import org.junit.jupiter.api.*;
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
    private TarefaRepository mockRepository;

    @InjectMocks
    private TarefaService mockService;

    private List<Tarefa> mockListTarefas;

    @BeforeAll
    void configuracao() {
        this.mockListTarefas = List.of(
                new Tarefa.Builder().
                        titulo("Comida").
                        descricao("Fazer um bolo").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(8, ChronoUnit.DAYS))
                        .build(),
                new Tarefa.Builder().
                        titulo("Facilit").
                        descricao("Front-end atualização componente").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(10, ChronoUnit.DAYS))
                        .build(),
                new Tarefa.Builder().
                        titulo("Facilit estágio").
                        descricao("Aprender react-native").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(22, ChronoUnit.DAYS))
                        .build()
        );

    }


    @Test
    @DisplayName("Deve retornar uma Lista com 3 Tarefas, quando chamar buscar todas.")
    void deveRetornarListaDeTarefasQuandoChamarOMetodoBuscarTodasComTotalDe3Tarefas() {
        given(mockRepository.findAll()).willReturn(mockListTarefas);
        int expectativaQuantidadeTarefas = 3;

        var listaTarefas = mockService.buscarTodas();

        then(mockRepository).should().findAll();

        assertNotNull(listaTarefas);
        assertEquals(expectativaQuantidadeTarefas, listaTarefas.size());

    }

    @Test
    @DisplayName("Deve retornar uma lista de tarefa vázia [], quando não existir informações banco de dados.")
    void deveRetornarListTarefasVaziaQuandoChamarOMetodoBuscarTodas() {
        given(mockRepository.findAll()).willReturn(List.of());

        var listaTarefas = mockService.buscarTodas();

        then(mockRepository).should().findAll();

        assertNotNull(listaTarefas);
        assertTrue(listaTarefas.isEmpty());

    }

    @Test
    @DisplayName("Deve retornar a tarefa, quando ela for persistida e tem que possuir um ID.")
    void deveRetornarATarefaComIdQuandoCriarUmaTarefaValida() {
        var idExpectativa = 1L;
        Tarefa tarefaCriadaExpectativa = new Tarefa.Builder().
                titulo("Tarefa teste").
                descricao("Estágio facilit").
                responsavel("Diogenes da Silva Santos").
                status(Status.FAZER).
                dataCriacao(Instant.now()).
                dataAtualizacao(Instant.now()).
                dataLimite(Instant.now().plus(8, ChronoUnit.DAYS))
                .build();

        given(mockRepository.save(any(Tarefa.class)))
                .willAnswer(invocation -> {
                    Tarefa tarefaMock = invocation.getArgument(0);
                    tarefaMock.setId(1L);
                    return tarefaMock;
                });

        var tarefaSalvaBD = mockService.salvar(tarefaCriadaExpectativa);

        then(mockRepository)
                .should().save(any(Tarefa.class));

        assertNotNull(tarefaSalvaBD);
        assertEquals(idExpectativa, tarefaSalvaBD.getId());

    }

    @Test
    @DisplayName("Deve retornar 2 tarefas, quando buscar por título Facilit")
    void deveRetornarListTarefaQuandoTituloCorrelacionado() {
        var tituloExpectativa = "Facilit";
        var quantidadeExpectativa = 2;

        given(mockRepository.buscarPorTitulo(any(String.class)))
                .willAnswer(invocation -> {
                    String tituloConsulta = invocation.getArgument(0);

                    return mockListTarefas
                            .stream()
                            .filter(tarefa -> tarefa.getTitulo().toUpperCase()
                                    .startsWith(tituloConsulta.toUpperCase()))
                            .toList();
                });

        var listTarefa = mockService.buscarPorTitulo(tituloExpectativa);

        then(mockRepository)
                .should().buscarPorTitulo(any(String.class));

        assertNotNull(listTarefa);
        assertEquals(quantidadeExpectativa, listTarefa.size());
    }

    @Test
    @DisplayName("Deve lançar a exception IllegalArgumentException, quando buscar por titulo for null.")
    void deveLancarIllegalArgumentExceptionQuandoBuscarComTituloNulo() {
        String expectativaTituloNulo = null;
        assertThrows(IllegalArgumentException.class,
                () -> mockService.buscarPorTitulo(expectativaTituloNulo),
                ()-> "Execução foi um sucesso, e se esperava uma exception IllegalArgumentException.");
    }

    @Test
    @DisplayName("Deve retornar uma lista de tarefa, quando buscar por descrição Apreender.")
    void deveRetornarListTarefaQuandoDescricaoCorrelacionado() {
        var tituloExpectativa = "Aprender";

        given(mockRepository.buscarPorDescricao(any(String.class)))
                .willAnswer(invocation -> {
                    String descricaoConsulta = invocation.getArgument(0);

                    return mockListTarefas
                            .stream()
                            .filter(tarefa -> tarefa.getDescricao().toUpperCase()
                                    .startsWith(descricaoConsulta.toUpperCase()))
                            .toList();
                });

        var listTarefa = mockService.buscarPorDescricao(tituloExpectativa);

        then(mockRepository)
                .should().buscarPorDescricao(any(String.class));

        assertNotNull(listTarefa);
        assertFalse(listTarefa.isEmpty());
        assertTrue(listTarefa.getFirst().getDescricao().contains(tituloExpectativa));
    }

    @Test
    @DisplayName("Deve lançar a exception IllegalArgumentException, quando buscar por descrição for null.")
    void deveLancarIllegalArgumentExceptionQuandoBuscarComDescricaoNulo() {
        String expectativaDescricaoNulo = null;
        assertThrows(IllegalArgumentException.class,
                () -> mockService.buscarPorDescricao(expectativaDescricaoNulo),
                () -> "Execução foi um sucesso, e se esperava uma exception IllegalArgumentException.");
    }




}
