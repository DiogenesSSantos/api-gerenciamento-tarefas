package com.github.diogenesssantos.facilittecnologia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.diogenesssantos.facilittecnologia.controller.TarefaController;
import com.github.diogenesssantos.facilittecnologia.controller.request.TarefaRequestDTO;
import com.github.diogenesssantos.facilittecnologia.exception.CampoInvalidoException;
import com.github.diogenesssantos.facilittecnologia.exception.TarefaNaoLocalizadaException;
import com.github.diogenesssantos.facilittecnologia.exceptionhandller.Problema;
import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import com.github.diogenesssantos.facilittecnologia.service.TarefaService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TarefaController.class)
@AutoConfigureMockMvc
public class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @MockitoBean
    private TarefaService mockTarefaService;

    private List<Tarefa> mockListTarefa;

    private Tarefa mockTarefa;

    @BeforeEach
    void configuracao() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockListTarefa = List.of(
                new Tarefa.Builder()
                        .titulo("Documentação API")
                        .descricao("Adicionar exemplos e contratos")
                        .responsavel("Diogenes da Silva Santos")
                        .status(Status.FAZER)
                        .dataCriacao(LocalDateTime.now())
                        .dataAtualizacao(LocalDateTime.now())
                        .dataLimite(LocalDateTime.now().plus(8, ChronoUnit.DAYS))
                        .build(),
                new Tarefa.Builder()
                        .titulo("Ajuste layout")
                        .descricao("Corrigir responsividade mobile")
                        .responsavel("Diogenes da Silva Santos")
                        .status(Status.FAZER)
                        .dataCriacao(LocalDateTime.now())
                        .dataAtualizacao(LocalDateTime.now())
                        .dataLimite(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
                        .build(),
                new Tarefa.Builder()
                        .titulo("Ajuste permissões")
                        .descricao("Rever roles e acessos")
                        .responsavel("Diogenes da Silva Santos")
                        .status(Status.PROGRESSO)
                        .dataCriacao(LocalDateTime.now())
                        .dataAtualizacao(LocalDateTime.now())
                        .dataLimite(LocalDateTime.now().plus(22, ChronoUnit.DAYS))
                        .build()
        );

        mockTarefa = new Tarefa.Builder()
                .titulo("Ajuste permissões")
                .descricao("Rever roles e acessos")
                .responsavel("Diogenes da Silva Santos")
                .status(Status.PROGRESSO)
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .dataLimite(LocalDateTime.now().plus(22, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS))
                .build();
    }

    @Test
    @DisplayName("Deve retornar um JSON list de tarefa," +
            " quando solicitar uma requisição GET no /tarefas.")
    void deve_Retornar_List_Tarefa_Quando_Fazer_uma_Requesicao_GET_tarefas() throws Exception {
        given(mockTarefaService.buscarTodas()).willReturn(mockListTarefa);

        var response = mockMvc.perform(get("/tarefas"));

        response.andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Deve retornar um JSON contendo uma tarefa," +
            " quando solicitar uma requisição GET no /tarefas/id/{id}.")
    void deve_Retornar_Uma_Tarefa_Quando_Fazer_uma_Requisicao_GET_tarefas_id() throws Exception {
        var idExpectativa = 14L;

        given(mockTarefaService.buscarPorId(any(Long.class))).willAnswer(invocation -> {
            mockTarefa.setId(14L);

            return mockTarefa;
        });

        var response = mockMvc.perform(get("/tarefas/id/{id}", idExpectativa));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").value(idExpectativa));
    }


    @Test
    @DisplayName("Deve retornar um JSON contendo um Problema not found com exception TarefaNaoLocalizadaException," +
            " quando solicitar uma requisição GET no /tarefas/id/{id} e o Id não correlacionar com uma tarefa.")
    void deve_Retornar_Um_Problema_Quando_Fazer_uma_Requisicao_GET_tarefas_id_Com_id_Que_Nao_exista() throws Exception {
        var idExpectativa = 14L;

        given(mockTarefaService.buscarPorId(any(Long.class)))
                .willThrow(new TarefaNaoLocalizadaException(
                        String.format("A tarefa com o id [%s] não existe no banco de dados.", idExpectativa), "id"));

        var response = mockMvc.perform(get("/tarefas/id/{id}", idExpectativa));
        var body = response.andReturn().getResponse().getContentAsString();
        Problema Problema = mapper.readValue(body, new TypeReference<>() {
        });

        response.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.classException").value(TarefaNaoLocalizadaException
                        .class.getSimpleName()));
        assertNotNull(Problema);
    }


    @Test
    @DisplayName("Deve retornar um JSON contendo uma tarefa," +
            " quando solicitar uma requisição GET no /tarefas/titulo.")
    void deve_Retornar_Uma_Tarefa_Quando_Fazer_uma_Requisicao_GET_tarefas_titulo() throws Exception {
        var tituloExpectativa = "Facilit estágio desafio";

        given(mockTarefaService.buscarPorTitulo(any(String.class))).willAnswer(invocation -> {
            mockTarefa.setId(15L);
            return mockTarefa;
        });

        var response = mockMvc.perform(get("/tarefas/titulo")
                .param("titulo", tituloExpectativa));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }


    @Test
    @DisplayName("Deve retornar um JSON contendo um Problema not found com a exception TarefaNaoLocalizadaException," +
            " quando solicitar uma requisição GET no /tarefas/titulo e o titulo não correlacionar com uma tarefa.")
    void deve_Retornar_Um_Problema_Quando_Fazer_uma_Requisicao_GET_tarefas_titulo() throws Exception {
        var tituloExpectativa = "Facilit estágio desafio";

        given(mockTarefaService.buscarPorTitulo(any(String.class)))
                .willThrow(new TarefaNaoLocalizadaException(
                        String.format("A tarefa com o titulo [%s] não existe no banco de dados.", tituloExpectativa),
                        "titulo"));

        var response = mockMvc.perform(get("/tarefas/titulo").param("titulo", tituloExpectativa));
        var body = response.andReturn().getResponse().getContentAsString();
        Problema Problema = mapper.readValue(body, new TypeReference<>() {
        });

        response.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.classException").value(TarefaNaoLocalizadaException
                        .class.getSimpleName()));
        assertNotNull(Problema);
    }


    @Test
    @DisplayName("Deve retornar um JSON contendo uma tarefa," +
            " quando solicitar uma requisição GET no /tarefas/descricao.")
    void deve_Retornar_Uma_Tarefa_Quando_Fazer_uma_Requisicao_GET_tarefas_descricao() throws Exception {
        var descricaoExpectativa = "Refatorar";

        given(mockTarefaService.buscarPorDescricao(any(String.class))).willAnswer(invocation -> {
            mockTarefa.setId(15L);
            return mockTarefa;
        });

        var response = mockMvc.perform(get("/tarefas/descricao")
                .param("descricao", descricaoExpectativa));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }


    @Test
    @DisplayName("Deve retornar um JSON contendo um Problema not found com a exception TarefaNaoLocalizadaException, " +
            "quando solicitar uma requisição GET no /tarefas/descricao e a descricao não correlacionar com uma tarefa.")
    void deve_Retornar_Um_Problema_Quando_Fazer_uma_Requisicao_GET_tarefas_descricao() throws Exception {
        var descricaoExpectativa = "desafio";

        given(mockTarefaService.buscarPorDescricao(any(String.class)))
                .willThrow(new TarefaNaoLocalizadaException(
                        String.format("A tarefa com o descricao [%s] não existe no banco de dados.",
                                descricaoExpectativa), "descricao"));

        var response = mockMvc.perform(get("/tarefas/descricao").param("descricao",
                descricaoExpectativa));
        var body = response.andReturn().getResponse().getContentAsString();
        Problema Problema = mapper.readValue(body, new TypeReference<>() {
        });

        response.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.classException").value(TarefaNaoLocalizadaException
                        .class.getSimpleName()));
        assertNotNull(Problema);
    }


    @Test
    @DisplayName("Deve retornar um JSON contendo um list tarefa," +
            " quando solicitar uma requisição GET no /tarefas/status.")
    void deve_Retornar_Uma_Tarefa_Quando_Fazer_uma_Requisicao_GET_tarefas_status() throws Exception {
        var statusExpectativa = Status.FAZER;

        given(mockTarefaService.buscarPorStatus(any(Status.class))).willAnswer(invocation ->
                mockListTarefa);

        var response = mockMvc.perform(get("/tarefas/status")
                .param("status", statusExpectativa.name()));

        response.andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Deve retornar um JSON contendo uma Tarefa," +
            " quando solicitar uma requisição POST no /tarefas.")
    void deve_Retornar_Uma_Tarefa_Com_ID_Quando_fazer_uma_Requisicao_POST_tarefas() throws Exception {
        var tarefaExpectativa = mockTarefa;

        given(mockTarefaService.salvar(any(Tarefa.class)))
                .willAnswer(invocation -> {
                    Tarefa mocktarefaBD = invocation.getArgument(0);
                    mocktarefaBD.setId(1L);
                    return mocktarefaBD;
                });

        var response = mockMvc.perform(post("/tarefas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tarefaExpectativa)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.titulo").value(tarefaExpectativa.getTitulo()))
                .andExpect(jsonPath("$.descricao").value(tarefaExpectativa.getDescricao()))
                .andExpect(jsonPath("$.status").value(tarefaExpectativa.getStatus().name()))
                .andExpect(jsonPath("$.dataLimite").value(tarefaExpectativa.getDataLimite().toString()));
    }


    @Test
    @DisplayName("Deve retornar um JSON contendo uma Tarefa atualizada parcial ou por completo," +
            " quando solicitar uma requisição PATCH no /tarefas/id/{id}.")
    void deve_Retornar_Uma_Tarefa_Com_Campo_Atualizado_Quando_fazer_uma_Requisicao_PATCH_tarefas_id()
            throws Exception {
        var idExpectativa = 1;
        var campoTituloAtualizadaExpectativa = "Atualizando titulo";
        var tarefaRequestDTO = new TarefaRequestDTO(campoTituloAtualizadaExpectativa,
                null,
                null,
                null,
                null);

        given(mockTarefaService.buscarPorId(any(Long.class)))
                .willAnswer(invocation -> {
                    Tarefa mocktarefaBD = mockTarefa;
                    mocktarefaBD.setId(1L);
                    return mocktarefaBD;
                }).getMock();

        given(mockTarefaService.atualizar(any(Tarefa.class), any(TarefaRequestDTO.class)))
                .willAnswer(invocation -> {
                    Tarefa mocktarefaBD = invocation.getArgument(0);
                    TarefaRequestDTO mockTarefaRequestDTO = invocation.getArgument(1);

                    if (mockTarefaRequestDTO.titulo() != null) mocktarefaBD.setTitulo(mockTarefaRequestDTO.titulo());
                    return mocktarefaBD;
                });

        var response = mockMvc.perform(patch("/tarefas/id/{id}", idExpectativa)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tarefaRequestDTO)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idExpectativa))
                .andExpect(jsonPath("$.titulo").value(tarefaRequestDTO.titulo()));
    }


    @Test
    @DisplayName("Deve retornar um JSON contendo uma Tarefa atualizada parcial ou por completo," +
            " quando solicitar uma requisição PATCH no /tarefas/titulo.")
    void deve_Retornar_Uma_Tarefa_Atualizada_Parcial_Ou_Por_Completa_Quando_fazer_uma_Requisicao_PATCH_tarefas_titulo()
            throws Exception {

        var campoTituloExpectativa = "Atualizando titulo";
        var tarefaRequestDTO = new TarefaRequestDTO("campoTituloAtualizadaExpectativa",
                null,
                null,
                null,
                null);

        given(mockTarefaService.buscarPorTitulo(any(String.class)))
                .willAnswer(invocation -> {
                    Tarefa mocktarefaBD = mockTarefa;
                    mocktarefaBD.setId(1L);
                    return mocktarefaBD;
                }).getMock();

        given(mockTarefaService.atualizar(any(Tarefa.class), any(TarefaRequestDTO.class)))
                .willAnswer(invocation -> {
                    Tarefa mocktarefaBD = invocation.getArgument(0);
                    TarefaRequestDTO mockTarefaRequestDTO = invocation.getArgument(1);

                    if (mockTarefaRequestDTO.titulo() != null) mocktarefaBD.setTitulo(mockTarefaRequestDTO.titulo());
                    return mocktarefaBD;
                });

        var response = mockMvc.perform(patch("/tarefas/titulo").param("titulo", campoTituloExpectativa)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tarefaRequestDTO)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.titulo").value(tarefaRequestDTO.titulo()));
    }


    @Test
    @DisplayName("Deve retornar um JSON contendo uma Tarefa atualizada parcial ou por completo," +
            " quando solicitar uma requisição PATCH no /tarefas/descricao.")
    void deve_Retornar_Uma_Tarefa_Atualizada_Parcial_Ou_Por_Completa_Quando_fazer_uma_Requisicao_PATCH_tarefas_descricao()
            throws Exception {

        var descricaoExpectativa = "Refatore a descricao";
        var tarefaRequestDTO = new TarefaRequestDTO(null,
                "Atualizando a descrição para melhor legibilidade.",
                null,
                null,
                null);

        given(mockTarefaService.buscarPorDescricao(any(String.class)))
                .willAnswer(invocation -> {
                    Tarefa mocktarefaBD = mockTarefa;
                    mocktarefaBD.setId(1L);
                    return mocktarefaBD;
                }).getMock();

        given(mockTarefaService.atualizar(any(Tarefa.class), any(TarefaRequestDTO.class)))
                .willAnswer(invocation -> {
                    Tarefa mocktarefaBD = invocation.getArgument(0);
                    TarefaRequestDTO mockTarefaRequestDTO = invocation.getArgument(1);

                    if (mockTarefaRequestDTO.descricao() != null) {
                        mocktarefaBD.setDescricao(mockTarefaRequestDTO.descricao());
                    }

                    return mocktarefaBD;
                });

        var response = mockMvc.perform(patch("/tarefas/descricao").param("descricao",
                        descricaoExpectativa)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tarefaRequestDTO)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.descricao").value(tarefaRequestDTO.descricao()));
    }


    @Test
    @DisplayName("Deve retornar um JSON contendo uma Tarefa com o campo status atualizado," +
            " quando solicitar uma requisição PATCH no /tarefas/status/{id}.")
    void deve_Retornar_Uma_Tarefa_Com_Campo_Status_Atualizado_Quando_fazer_uma_Requisicao_PATCH_tarefas()
            throws Exception {

        var idExpectativa = 1;
        var statusExpectativa = Status.CONCLUIDO;
        var tarefaRequestDTO = new TarefaRequestDTO(null,
                null,
                null,
                statusExpectativa,
                null);

        given(mockTarefaService.buscarPorId(any(Long.class)))
                .willAnswer(invocation -> {
                    Tarefa mocktarefaBD = mockTarefa;
                    mocktarefaBD.setId(1L);
                    return mocktarefaBD;
                }).getMock();

        given(mockTarefaService.atualizar(any(Tarefa.class), any(TarefaRequestDTO.class)))
                .willAnswer(invocation -> {
                    Tarefa mocktarefaBD = invocation.getArgument(0);
                    TarefaRequestDTO mockTarefaRequestDTO = invocation.getArgument(1);

                    if (mockTarefaRequestDTO.status() != null) mocktarefaBD.setStatus(mockTarefaRequestDTO.status());
                    return mocktarefaBD;
                });

        var response = mockMvc.perform(patch("/tarefas/status/{id}", idExpectativa)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tarefaRequestDTO)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idExpectativa))
                .andExpect(jsonPath("$.status").value(tarefaRequestDTO.status().name()));
    }


}
