package com.github.diogenesssantos.facilittecnologia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.diogenesssantos.facilittecnologia.controller.TarefaController;
import com.github.diogenesssantos.facilittecnologia.controller.response.TarefaResponseDTO;
import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import com.github.diogenesssantos.facilittecnologia.service.TarefaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
                        .dataCriacao(Instant.now())
                        .dataAtualizacao(Instant.now())
                        .dataLimite(Instant.now().plus(8, ChronoUnit.DAYS))
                        .build(),
                new Tarefa.Builder()
                        .titulo("Ajuste layout")
                        .descricao("Corrigir responsividade mobile")
                        .responsavel("Diogenes da Silva Santos")
                        .status(Status.FAZER)
                        .dataCriacao(Instant.now())
                        .dataAtualizacao(Instant.now())
                        .dataLimite(Instant.now().plus(10, ChronoUnit.DAYS))
                        .build(),
                new Tarefa.Builder()
                        .titulo("Ajuste permissões")
                        .descricao("Rever roles e acessos")
                        .responsavel("Diogenes da Silva Santos")
                        .status(Status.PROGRESSO)
                        .dataCriacao(Instant.now())
                        .dataAtualizacao(Instant.now())
                        .dataLimite(Instant.now().plus(22, ChronoUnit.DAYS))
                        .build()
        );
    }

    @Test
    @DisplayName("teste controller buscarTodas")
    void deve_Retornar_Listar_Tarefa_Quando_buscarTodas() throws Exception {
        given(mockTarefaService.buscarTodas()).willReturn(mockListTarefa);

        var quantidadeListaExpectativa = 3;
        given(mockTarefaService.buscarTodas()).willReturn(mockListTarefa);

        var response = mockMvc.perform(get("/tarefas"));
        String responseBody = response.andReturn().getResponse().getContentAsString();
        List<TarefaResponseDTO> listTarefaResponseDTOS = mapper.readValue(responseBody, new TypeReference<>() {});

        response.andDo(print())
                .andExpect(status().isOk());
        Assertions.assertEquals(quantidadeListaExpectativa, listTarefaResponseDTOS.size(),
                () -> "Esperava-se 3 itens na lista, mas foi obtido outro resultado");
    }
}
