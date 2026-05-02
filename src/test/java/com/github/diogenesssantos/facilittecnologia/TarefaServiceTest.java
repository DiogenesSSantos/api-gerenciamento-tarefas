package com.github.diogenesssantos.facilittecnologia;

import com.github.diogenesssantos.facilittecnologia.exceptionhandller.exception.TarefaNotFoundException;
import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import com.github.diogenesssantos.facilittecnologia.repository.TarefaRepository;
import com.github.diogenesssantos.facilittecnologia.service.TarefaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.BDDMockito.*;


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
    @DisplayName("Deve retornar uma list com 3 tarefa, quando chamar o método buscar todas.")
    void deveRetornarListaDeTarefasQuandoChamarOMetodoBuscarTodasComTotalDe3Tarefas() {
        given(mockRepository.findAll()).willReturn(mockListTarefas);
        int expectativaQuantidadeTarefas = 3;

        var listaTarefasBD = mockService.buscarTodas();

        then(mockRepository).should().findAll();

        assertNotNull(listaTarefasBD);
        assertEquals(expectativaQuantidadeTarefas, listaTarefasBD.size());

    }

    @Test
    @DisplayName("Deve retornar uma list de tarefa vázia [], quando não existir tarefa no banco de dados.")
    void deveRetornarListTarefasVaziaQuandoChamarOMetodoBuscarTodas() {
        given(mockRepository.findAll()).willReturn(List.of());

        var listaTarefasBD = mockService.buscarTodas();

        then(mockRepository).should().findAll();

        assertNotNull(listaTarefasBD);
        assertTrue(listaTarefasBD.isEmpty());

    }

    @Test
    @DisplayName("Deve retornar a tarefa, " +
            "quando ela for persistida com o método salvar, e ela tem que possuir um ID após retorno.")
    void deve_Retornar_A_Tarefa_Com_Id_Quando_Criar_Uma_TarefaValida() {
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
    @DisplayName("Deve retornar uma tarefa, quando chamar o método buscar por título correlacionarem.")
    void deve_Retornar_Uma_Tarefa_Quando_O_Titulo_Correlacionarem() {
        var tituloExpectativa = "Facilit";

        given(mockRepository.buscarPorTitulo(any(String.class)))
                .willAnswer(invocation -> {
                    String tituloConsulta = invocation.getArgument(0);

                    List<Tarefa> list = mockListTarefas
                            .stream()
                            .filter(tarefa -> tarefa.getTitulo().equalsIgnoreCase(tituloConsulta))
                            .limit(1)
                            .toList();

                    return list.isEmpty() ? Optional.empty() : Optional.of(list.getFirst());
                });

        Tarefa tarefaSalvaDB = mockService.buscarPorTitulo(tituloExpectativa);

        then(mockRepository)
                .should().buscarPorTitulo(any(String.class));
        assertNotNull(tarefaSalvaDB);
        assertEquals(tituloExpectativa, tarefaSalvaDB.getTitulo());
    }

    @Test
    @DisplayName("Deve lançar uma exception TarefaNotFoundException, quando chamar o método buscar por titulo" +
            "passando como parâmetro um titulo que não exista no banco de dados.")
    void deve_Lancar_TarefaNotFoundException_Quando_BuscarComTitulo_O_TituloNaoExistir() {
        var tituloNaoExistente = "Testando titulo invalido";
        var msgExceptionExpectativa = String.format("Tarefa com  o titulo [%s] não existe.", tituloNaoExistente);

        given(mockRepository.buscarPorTitulo(any(String.class)))
                .willThrow(new TarefaNotFoundException(msgExceptionExpectativa));

        TarefaNotFoundException tarefaNotFoundException = assertThrows(TarefaNotFoundException.class,
                () -> mockService.buscarPorTitulo(tituloNaoExistente));


        then(mockRepository).should().buscarPorTitulo(any(String.class));
        assertNotNull(tarefaNotFoundException);
        assertEquals(msgExceptionExpectativa, tarefaNotFoundException.getMessage());
    }


    @Test
    @DisplayName("Deve lançar a exception IllegalArgumentException, quando chamar o método buscar por titulo" +
            "e o parâmetro for null.")
    void deveLancarIllegalArgumentExceptionQuandoBuscarComTituloNulo() {
        String expectativaTituloNulo = null;
        assertThrows(IllegalArgumentException.class,
                () -> mockService.buscarPorTitulo(expectativaTituloNulo),
                () -> "Execução foi um sucesso, e se esperava uma exception IllegalArgumentException.");
    }

    @Test
    @DisplayName("Deve atualizar uma tarefa, " +
            "quando chamar o método atualizar por titulo e todos campos estejam validos.")
    void deve_Atualizar_Tarefa_Quando_Chamar_Metodo_Atualizar_Por_Titulo_E_Todos_Os_Campos_Forem_Validos() {
        var tituloExpectativa = "Facilit";
        var atualizandoTituloExpectativa = "Facilit estágio";

        Tarefa tarefaExistente = getTarefaMock();
        Tarefa tarefaAtualizada = new Tarefa.Builder()
                .titulo(atualizandoTituloExpectativa)
                .descricao(tarefaExistente.getDescricao())
                .responsavel(tarefaExistente.getResponsavel())
                .status(tarefaExistente.getStatus())
                .dataCriacao(tarefaExistente.getDataCriacao())
                .dataAtualizacao(Instant.now())
                .dataLimite(tarefaExistente.getDataLimite())
                .build();

        given(mockRepository.buscarPorTitulo(any(String.class)))
                .willReturn(Optional.of(tarefaExistente));

        given(mockRepository.save(any(Tarefa.class)))
                .willAnswer(invocation -> {
                    Tarefa mockTarefa = invocation.getArgument(0);
                    mockTarefa.setId(123L);
                    return mockTarefa;
                });

        var tarefaSalvaDB = mockService.atualizarPorTitulo(tituloExpectativa, tarefaAtualizada);

        then(mockRepository).should().buscarPorTitulo(any(String.class));
        then(mockRepository).should().save(any(Tarefa.class));
        assertNotNull(tarefaSalvaDB);
        assertNotNull(tarefaSalvaDB.getId());
        assertEquals(atualizandoTituloExpectativa, tarefaSalvaDB.getTitulo());
    }


    @Test
    @DisplayName("Deve lançar a exception TarefaNotFound, quando chamar o método atualizar por titulo e a tarefa" +
            "não existir no banco de dados.")
    void deve_Lancar_TarefaNotFound_Quando_For_Atualizar_Uma_Tarefa_Com_Um_Titulo_Que_Nao_Existe_No_Banco_De_Dados() {
        var tituloExpectativa = "Não existe titulo";
        var atualizandoTituloExpectativa = "Facilit estágio";

        Tarefa tarefaExistente = getTarefaMock();
        Tarefa tarefaAtualizada = new Tarefa.Builder()
                .titulo(atualizandoTituloExpectativa)
                .descricao(tarefaExistente.getDescricao())
                .responsavel(tarefaExistente.getResponsavel())
                .status(tarefaExistente.getStatus())
                .dataCriacao(tarefaExistente.getDataCriacao())
                .dataAtualizacao(Instant.now())
                .dataLimite(tarefaExistente.getDataLimite())
                .build();

        given(mockRepository.buscarPorTitulo(any(String.class)))
                .willReturn(Optional.empty());


        assertThrows(TarefaNotFoundException.class, () -> {
            mockService.atualizarPorTitulo(tituloExpectativa, tarefaAtualizada);
        });

        then(mockRepository).should().buscarPorTitulo(any(String.class));


    }


    @Test
    @DisplayName("Deve retornar uma tarefa, quando chamar o método buscar por descrição correlacionarem.")
    void deveRetornarListTarefaQuandoDescricaoCorrelacionado() {
        var descricaoExpectativa = "Aprender react-native";

        given(mockRepository.buscarPorDescricao(any(String.class)))
                .willAnswer(invocation -> {
                    String tituloConsulta = invocation.getArgument(0);

                    List<Tarefa> list = mockListTarefas
                            .stream()
                            .filter(tarefa -> tarefa.getDescricao().equalsIgnoreCase(tituloConsulta))
                            .limit(1)
                            .toList();

                    return list.isEmpty() ? Optional.empty() : Optional.of(list.getFirst());
                });

        Tarefa tarefaSalvaDB = mockService.buscarPorDescricao(descricaoExpectativa);

        then(mockRepository)
                .should().buscarPorDescricao(any(String.class));

        assertNotNull(tarefaSalvaDB);
        assertEquals(descricaoExpectativa, tarefaSalvaDB.getDescricao());

    }

    @Test
    @DisplayName("Deve atualizar uma tarefa, " +
            "quando chamar o método atualizar por descrição e todos campos estejam validos.")
    void deve_Atualizar_Tarefa_Quando_Chamar_Metodo_Atualizar_Por_Descricao_E_Todos_Os_Campos_Forem_Validos() {
        var descricaoExpectativa = "Facilit";
        var atualizandoDescricaoExpectativa = "Mudando a descrição da tarefa";

        Tarefa tarefaExistente = getTarefaMock();
        Tarefa tarefaAtualizada = new Tarefa.Builder()
                .titulo(tarefaExistente.getTitulo())
                .descricao(atualizandoDescricaoExpectativa)
                .responsavel(tarefaExistente.getResponsavel())
                .status(tarefaExistente.getStatus())
                .dataCriacao(tarefaExistente.getDataCriacao())
                .dataAtualizacao(Instant.now())
                .dataLimite(tarefaExistente.getDataLimite())
                .build();

        given(mockRepository.buscarPorDescricao(any(String.class)))
                .willReturn(Optional.of(tarefaExistente));

        given(mockRepository.save(any(Tarefa.class)))
                .willAnswer(invocation -> {
                    Tarefa mockTarefa = invocation.getArgument(0);
                    mockTarefa.setId(123L);
                    return mockTarefa;
                });

        var tarefaSalvaDB = mockService.atualizarPorDescricao(descricaoExpectativa, tarefaAtualizada);

        then(mockRepository).should().buscarPorDescricao(any(String.class));
        then(mockRepository).should().save(any(Tarefa.class));
        assertNotNull(tarefaSalvaDB);
        assertNotNull(tarefaSalvaDB.getId());
        assertEquals(atualizandoDescricaoExpectativa, tarefaSalvaDB.getDescricao());
    }


    @Test
    @DisplayName("Deve lançar a exception TarefaNotFound, quando chamar o método atualizar por descrição e a tarefa" +
            "não existir no banco de dados.")
    void deve_Lancar_TarefaNotFound_Quando_For_Atualizar_Uma_Tarefa_Com_Um_Descriaco_Que_Nao_Existe_No_Banco_De_Dados() {
        var descricaoExpectativa = "Não existe descrição";
        var atualizandoDescricaoExpectativa = "Facilit estágio";

        Tarefa tarefaExistente = getTarefaMock();
        Tarefa tarefaAtualizada = new Tarefa.Builder()
                .titulo(tarefaExistente.getTitulo())
                .descricao(atualizandoDescricaoExpectativa)
                .responsavel(tarefaExistente.getResponsavel())
                .status(tarefaExistente.getStatus())
                .dataCriacao(tarefaExistente.getDataCriacao())
                .dataAtualizacao(Instant.now())
                .dataLimite(tarefaExistente.getDataLimite())
                .build();

        assertThrows(TarefaNotFoundException.class, () -> {
            mockService.atualizarPorDescricao(descricaoExpectativa, tarefaAtualizada);
        });

        then(mockRepository).should().buscarPorDescricao(any(String.class));


    }


    @Test
    @DisplayName("Deve lançar a exception IllegalArgumentException, quando chamar o método buscar por descrição " +
            "e o parâmetro for null.")
    void deveLancarIllegalArgumentExceptionQuandoBuscarComDescricaoNulo() {
        String expectativaDescricaoNulo = null;
        assertThrows(IllegalArgumentException.class,
                () -> mockService.buscarPorDescricao(expectativaDescricaoNulo),
                () -> "Execução foi um sucesso, e se esperava uma exception IllegalArgumentException.");
    }


    private Tarefa getTarefaMock() {
        return mockListTarefas.getFirst();
    }
}
