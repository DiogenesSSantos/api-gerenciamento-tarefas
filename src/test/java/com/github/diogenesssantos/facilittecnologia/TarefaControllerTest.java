package com.github.diogenesssantos.facilittecnologia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.diogenesssantos.facilittecnologia.controller.response.TarefaResponseDTO;
import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import com.github.diogenesssantos.facilittecnologia.service.TarefaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TarefaControllerTest.class)
public class TarefaControllerTest {

    private Tarefa mockTarefa;
    private List<Tarefa> mockListTarefa;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @MockitoBean
    private TarefaService mocktTarefaService;

    @BeforeEach
    void configuracao() {
        mockListTarefa = List.of(
                new Tarefa.Builder().
                        titulo("Documentação API").
                        descricao("Adicionar exemplos e contratos").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(8, ChronoUnit.DAYS))
                        .build(),
                new Tarefa.Builder().
                        titulo("Ajuste layout").
                        descricao("Corrigir responsividade mobile").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.FAZER).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(10, ChronoUnit.DAYS))
                        .build(),
                new Tarefa.Builder().
                        titulo("Ajuste permissões").
                        descricao("Rever roles e acessos").
                        responsavel("Diogenes da Silva Santos").
                        status(Status.PROGRESSO).
                        dataCriacao(Instant.now()).
                        dataAtualizacao(Instant.now()).
                        dataLimite(Instant.now().plus(22, ChronoUnit.DAYS))
                        .build()
        );
    }


    @Test
    @DisplayName("teste repository buscarTodos")
    void deve_Retornar_Listar_Tarefa_Quando_buscarTodos() throws Exception {
        var quantidadeListaExpectativa = 3;
        given(mocktTarefaService.buscarTodas()).willReturn(mockListTarefa);

        var response = mockMvc.perform(get("/tarefas"));
        String body = response.andReturn().getResponse().getContentAsString();
        List<TarefaResponseDTO> listTarefaResponseDTOS = mapper.readValue(body, new TypeReference<>(){});

        response.andDo(print())
                .andExpect(status().isOk());
        Assertions.assertEquals(quantidadeListaExpectativa, listTarefaResponseDTOS.size(),
                ()-> "Esperava-se 3 itens na lista, mas foi obtido outro resultado");

    }

}
