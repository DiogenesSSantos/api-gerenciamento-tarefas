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

import java.time.LocalDateTime;
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
                        dataCriacao(LocalDateTime.now()).
                        dataAtualizacao(LocalDateTime.now()).
                        dataLimite(LocalDateTime.now().plus(8, ChronoUnit.DAYS))
                        .build(),
                new Tarefa.Builder().
                        titulo("Facilit").
                        descricao("Front-end atualização componente").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(LocalDateTime.now()).
                        dataAtualizacao(LocalDateTime.now()).
                        dataLimite(LocalDateTime.now().plus(10, ChronoUnit.DAYS))
                        .build(),
                new Tarefa.Builder().
                        titulo("Facilit estágio").
                        descricao("Aprender react-native").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.PROGRESSO).
                        dataCriacao(LocalDateTime.now()).
                        dataAtualizacao(LocalDateTime.now()).
                        dataLimite(LocalDateTime.now().plus(22, ChronoUnit.DAYS))
                        .build()
        );

    }


    @Test
    @DisplayName("Deve retornar uma list com 3 Tarefa, quando chamar o método buscarTodas.")
    void deve_Retornar_Lista_De_Tarefas_Quando_BuscarTodas() {
        given(mockRepository.findAll()).willReturn(mockListTarefas);
        int expectativaQuantidadeTarefas = 3;

        var listaTarefasBD = mockService.buscarTodas();

        then(mockRepository).should().findAll();

        assertNotNull(listaTarefasBD);
        assertEquals(expectativaQuantidadeTarefas, listaTarefasBD.size());

    }

    @Test
    @DisplayName("Deve retornar uma list de tarefa vázia []," +
            " quando chamar o método buscarTodas e não existir nenhuma tarefa no banco de dados.")
    void deve_Retornar_List_Tarefas_Vazia_Quando_BuscarTodas() {
        given(mockRepository.findAll()).willReturn(List.of());

        var listaTarefasBD = mockService.buscarTodas();

        then(mockRepository).should().findAll();

        assertNotNull(listaTarefasBD);
        assertTrue(listaTarefasBD.isEmpty());

    }

    @Test
    @DisplayName("Deve retornar uma tarefa, " +
            "quando chamar o método salvar e todos os campos estejam validos, " +
            "e a tarefa retornada tem que possuir um ID.")
    void deve_Retornar_A_Tarefa_Salva_Com_Id_Quando_Salvar_Uma_Tarefa_Valida() {
        var idExpectativa = 1L;
        Tarefa tarefaCriadaExpectativa = new Tarefa.Builder().
                titulo("Tarefa teste").
                descricao("Estágio facilit").
                responsavel("Diogenes da Silva Santos").
                status(Status.FAZER).
                dataCriacao(LocalDateTime.now()).
                dataAtualizacao(LocalDateTime.now()).
                dataLimite(LocalDateTime.now().plus(8, ChronoUnit.DAYS))
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
    @DisplayName("Deve retornar uma tarefa, quando chamar o método buscarPorId" +
            "e o parâmetro id correlacionar a uma tarefa no banco de dados.")
    void deve_Retornar_Uma_Tarefa_Quando_BuscarPorId_E_O_id_exista_no_Banco_De_Dados() {
        var idValidoExpectativa = 1L;

        given(mockRepository.findById(any(Long.class)))
                .willAnswer(invocation -> {
                    Long mockIdTarefa = invocation.getArgument(0);
                    var mockTarefa = getTarefaMock();
                    mockTarefa.setId(mockIdTarefa);

                    return Optional.of(mockTarefa);
                });

        var tarefaBd = mockService.buscarPorId(idValidoExpectativa);

        then(mockRepository).should().findById(any(Long.class));
        assertNotNull(tarefaBd);
        assertEquals(idValidoExpectativa, tarefaBd.getId());

    }


    @Test
    @DisplayName("Deve lançar um TarefaNotFound, quando chamar o método buscarPorId" +
            "e o parâmetro id não correlacionar a uma tarefa no banco de dados.")
    void deve_Lancar_TarefaNotFoundException_Quando_BuscarPorId_E_O_Id_Nao_Existir_no_Banco_De_Dados() {
        var idValidoExpectativa = 1L;

        given(mockRepository.findById(any(Long.class)))
                .willAnswer(invocation -> {
                    return Optional.empty();
                });

        assertThrows(TarefaNotFoundException.class,
                () -> mockService.buscarPorId(idValidoExpectativa),
                () -> "Esperava-se um TarefaNotFoundException, mas gerou outro resultado.");

        then(mockRepository).should().findById(any(Long.class));
    }


    @Test
    @DisplayName("Deve lançar um IllegalArgumentException, quando chamar o método buscarPorId" +
            "e o parâmetro id for null.")
    void deve_Lancar_IllegalArgumentException_Quando_BuscarPorId_E_O_Id_Seja_Null() {
        Long idValidoExpectativa = null;

        assertThrows(IllegalArgumentException.class,
                () -> mockService.buscarPorId(idValidoExpectativa),
                () -> "Esperava-se um TarefaNotFoundException, mas gerou outro resultado.");

    }


    @Test
    @DisplayName("Deve retornar uma tarefa com status atualizado, quando chamar o método atualizarPorId" +
            "e o parâmetro id correlacionar a uma tarefa no banco de dados.")
    void deve_Retornar_Uma_Tarefa_Com_Status_Atualizado_Quando_atualizarStatusPorId_E_O_id_exista_no_Banco_De_Dados() {
        var idValidoExpectativa = 1L;
        var statusExpectativa = Status.CONCLUIDO;

        given(mockRepository.findById(any(Long.class)))
                .willReturn(Optional.of(mockListTarefas.getFirst()));

        given(mockRepository.save(any(Tarefa.class)))
                .willAnswer(invocation -> {
                    Tarefa mockTarefa = invocation.getArgument(0);
                    return mockTarefa;
                });

        var tarefaBD = mockService.AtualizarStatusPorId(idValidoExpectativa, statusExpectativa);

        then(mockRepository).should().findById(any(Long.class));
        then(mockRepository).should().save(any(Tarefa.class));
        assertNotNull(tarefaBD);
        assertEquals(statusExpectativa,tarefaBD.getStatus());
    }



    @Test
    @DisplayName("Deve retornar uma tarefa, quando chamar o método buscarPorTítulo" +
            " e o parâmetro titulo correlacionar a uma tarefa no banco de dados.")
    void deve_Retornar_Uma_Tarefa_Quando_BuscarPorTitulo_O_Titulo_Correlacionarem() {
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
    @DisplayName("Deve lançar uma exception TarefaNotFoundException, quando chamar o método buscarPorTitulo" +
            "passando como parâmetro um titulo que não correlacione a uma Tarefa no banco de dados.")
    void deve_Lancar_TarefaNotFoundException_Quando_BuscarComTitulo_E_O_Titulo_Nao_Existir_Uma_Tarefa_Correlacionada() {
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
    @DisplayName("Deve lançar a exception IllegalArgumentException, quando chamar o método buscarPorTitulo" +
            "e o parâmetro titulo for null.")
    void deve_Lancar_IllegalArgumentException_Quando_BuscarComTitulo_O_Titulo_For_Null() {
        String expectativaTituloNulo = null;
        assertThrows(IllegalArgumentException.class,
                () -> mockService.buscarPorTitulo(expectativaTituloNulo),
                () -> "Execução foi um sucesso, e se esperava uma exception IllegalArgumentException.");
    }

    @Test
    @DisplayName("Deve retornar uma tarefa atualizada, " +
            "quando chamar o método atualizarPorTitulo e o titulo correlacionar a uma uma tarefa existente" +
            "no banco de dados, e também todos os campos estejam válidos.")
    void deve_Retornar_A_Tarefa_Atualizada_Quando_AtualizarPorTitulo_E_Todos_Os_Campos_Estejam_Validos() {
        var tituloExpectativa = "Facilit";
        var atualizandoTituloExpectativa = "Facilit estágio";

        Tarefa tarefaExistente = getTarefaMock();
        Tarefa tarefaAtualizada = new Tarefa.Builder()
                .titulo(atualizandoTituloExpectativa)
                .descricao(tarefaExistente.getDescricao())
                .responsavel(tarefaExistente.getResponsavel())
                .status(tarefaExistente.getStatus())
                .dataCriacao(tarefaExistente.getDataCriacao())
                .dataAtualizacao(LocalDateTime.now())
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
    @DisplayName("Deve lançar a exception TarefaNotFound, quando chamar o método atualizarPorTitulo" +
            " e o parâmetro titulo não correlacionem a uma tarefa no banco de dados.")
    void deve_Lancar_TarefaNotFound_Quando_AtualizarPorTitulo_O_Titulo_Nao_Correlacionar_A_Uma_Tarefa() {
        var tituloExpectativa = "Não existe titulo";
        var atualizandoTituloExpectativa = "Facilit estágio";

        Tarefa tarefaExistente = getTarefaMock();
        Tarefa tarefaAtualizada = new Tarefa.Builder()
                .titulo(atualizandoTituloExpectativa)
                .descricao(tarefaExistente.getDescricao())
                .responsavel(tarefaExistente.getResponsavel())
                .status(tarefaExistente.getStatus())
                .dataCriacao(tarefaExistente.getDataCriacao())
                .dataAtualizacao(LocalDateTime.now())
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
    @DisplayName("Deve retornar uma tarefa, quando chamar o método buscarPordescricao " +
            "e o parâmetro descrição correlacione a uma tarefa no banco de dados.")
    void deve_Retornar_A_Tarefa_Quando_BuscarPorDescricao_A_Descricao_Correlacionarem() {
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
    @DisplayName("Deve retornar uma tarefa atualizada, " +
            "quando chamar o método atualizarPorDescricao e a descrição correlacionar a uma uma tarefa existente" +
            "no banco de dados, e também todos os campos estejam válidos.")
    void deve_Retornar_Uma_Tarefa_Atualizada_Quando_AtualizarPorDescricao_E_Todos_Os_Campos_estejam_Validos() {
        var descricaoExpectativa = "Facilit";
        var atualizandoDescricaoExpectativa = "Mudando a descrição da tarefa";

        Tarefa tarefaExistente = getTarefaMock();
        Tarefa tarefaAtualizada = new Tarefa.Builder()
                .titulo(tarefaExistente.getTitulo())
                .descricao(atualizandoDescricaoExpectativa)
                .responsavel(tarefaExistente.getResponsavel())
                .status(tarefaExistente.getStatus())
                .dataCriacao(tarefaExistente.getDataCriacao())
                .dataAtualizacao(LocalDateTime.now())
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
    @DisplayName("Deve lançar a exception TarefaNotFound, quando chamar o método atualizarPorDescricao " +
            " e descrição não correlacionar a nenhuma tarefa no banco de dados.")
    void deve_Lancar_TarefaNotFound_Quando_AtualizarPorDescricao_E_A_Tarefa_Nao_Existe_No_Banco_De_Dados() {
        var descricaoExpectativa = "Não existe essa descrição correlacionada a uma tarefa";
        var atualizandoDescricaoExpectativa = "Facilit estágio";

        Tarefa tarefaExistente = getTarefaMock();
        Tarefa tarefaAtualizada = new Tarefa.Builder()
                .titulo(tarefaExistente.getTitulo())
                .descricao(atualizandoDescricaoExpectativa)
                .responsavel(tarefaExistente.getResponsavel())
                .status(tarefaExistente.getStatus())
                .dataCriacao(tarefaExistente.getDataCriacao())
                .dataAtualizacao(LocalDateTime.now())
                .dataLimite(tarefaExistente.getDataLimite())
                .build();

        assertThrows(TarefaNotFoundException.class, () -> {
            mockService.atualizarPorDescricao(descricaoExpectativa, tarefaAtualizada);
        });

        then(mockRepository).should().buscarPorDescricao(any(String.class));


    }


    @Test
    @DisplayName("Deve lançar a exception IllegalArgumentException, quando chamar o método buscarPorDescricao " +
            "e o parâmetro descrição for null.")
    void deve_Lancar_IllegalArgumentException_Quando_BuscarComDescricao_O_Parametro_Descricao_Null() {
        String expectativaDescricaoNulo = null;
        assertThrows(IllegalArgumentException.class,
                () -> mockService.buscarPorDescricao(expectativaDescricaoNulo),
                () -> "Execução foi um sucesso, e se esperava uma exception IllegalArgumentException.");
    }


    @Test
    @DisplayName("Deve retornar uma list de Tarefa, quando chamar o método buscarPorStatus" +
            " e o parâmetro status se correlacionarem.")
    void deve_Retornar_Uma_Lista_De_Tarefas_Quando_buscarPorStatus_Que_Correlacionem() {
        var statusAFazerExpectativa = Status.FAZER;


        given(mockRepository.buscarPorStatus(any(Status.class)))
                .willAnswer(invocation -> {
                    Status statusParametro = invocation.getArgument(0);

                    return mockListTarefas.stream()
                            .filter(tarefa -> tarefa.getStatus().equals(statusParametro))
                            .toList();
                });


        var listaTarefaAFazerBD = mockService.buscarPorStatus(statusAFazerExpectativa);

        then(mockRepository).should().buscarPorStatus(any(Status.class));
        assertNotNull(listaTarefaAFazerBD);
        assertFalse(listaTarefaAFazerBD.isEmpty());

    }

    @Test
    @DisplayName("Deve retornar uma list de Tarefa vázia [], quando chamar o método buscarPorStatus" +
            " passando como parâmetro um status que não exista Tarefa correspondentes no banco de dados.")
    void deve_Retornar_Uma_Lista_De_Tarefas_Vazia_Quando_buscarPorStatus_Nao_Existir_O_Status_No_Banco_De_Dados() {
        var statusAFazerExpectativa = Status.CONCLUIDO;

        given(mockRepository.buscarPorStatus(any(Status.class)))
                .willAnswer(invocation -> {
                    Status statusParametro = invocation.getArgument(0);

                    return mockListTarefas.stream()
                            .filter(tarefa -> tarefa.getStatus().equals(statusParametro))
                            .toList();
                });

        var listaTarefaAFazerBD = mockService.buscarPorStatus(statusAFazerExpectativa);

        then(mockRepository).should().buscarPorStatus(any(Status.class));
        assertNotNull(listaTarefaAFazerBD);
        assertTrue(listaTarefaAFazerBD.isEmpty());

    }


    @Test
    @DisplayName("Deve retornar uma Tarefa com status atualizado, " +
            "quando chamar o método atualizarStatus e os parâmetros status e titulo estiverem válidos.")
    void deve_Retornar_Uma_Tarefa_Com_Status_Atualizado_Quando_AtualizarStatus_PorTitulo_Os_Parametros_Estejam_Validos() {
        var tituloExpextativa = "Facilit";
        var statusExpectativa = Status.PROGRESSO;


        given(mockRepository.buscarPorTitulo(any(String.class)))
                .willReturn(Optional.of(mockListTarefas.get(1)));

        given(mockRepository.save(any(Tarefa.class))).willAnswer(invocation -> {
            var mockTarefa = invocation.getArgument(0);
            return mockTarefa;
        });


        var tarefaBD = mockService.atualizarStatusPorTitulo(tituloExpextativa, statusExpectativa);

        then(mockRepository).should().buscarPorTitulo(any(String.class));
        then(mockRepository).should().save(any(Tarefa.class));
        assertNotNull(tarefaBD);
        assertEquals(tituloExpextativa, tarefaBD.getTitulo());
        assertEquals(statusExpectativa, tarefaBD.getStatus());

    }


    @Test
    @DisplayName("Deve lançar IllegalArgumentException, " +
            "quando chamar o método atualizarStatus e o parâmetro titulo for null.")
    void deve_Lancar_IllegalArgumentException_Quando_AtualizarStatus_PorTitulo_O_Titulo_Null() {
        String tituloNaoExisteExpectativa = null;
        var statusExpectativa = Status.PROGRESSO;

        assertThrows(IllegalArgumentException.class, () ->
                mockService.atualizarStatusPorTitulo(tituloNaoExisteExpectativa, statusExpectativa));

    }


    @Test
    @DisplayName("Deve lançar TarefaNotFoundException, " +
            "quando chamar o método atualizarStatus e o parâmetro titulo não correlacionar a uma Tarefa.")
    void deve_Lancar_TarefaNotFoundException_Quando_AtualizarStatus_PorTitulo_O_Titulo_Nao_Correlacionar_A_Uma_Tarefa() {
        String tituloNaoExisteExpectativa = "Facilit não existe BD";
        var statusExpectativa = Status.PROGRESSO;

        given(mockRepository.buscarPorTitulo(any(String.class)))
                .willReturn(Optional.empty());

        assertThrows(TarefaNotFoundException.class, () ->
                mockService.atualizarStatusPorTitulo(tituloNaoExisteExpectativa, statusExpectativa));

        then(mockRepository).should().buscarPorTitulo(any(String.class));

    }


    private Tarefa getTarefaMock() {
        return mockListTarefas.getFirst();
    }
}

