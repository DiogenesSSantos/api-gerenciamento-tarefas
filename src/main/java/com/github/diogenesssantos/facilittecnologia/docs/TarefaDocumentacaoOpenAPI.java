package com.github.diogenesssantos.facilittecnologia.docs;


import com.github.diogenesssantos.facilittecnologia.controller.request.TarefaRequestDTO;
import com.github.diogenesssantos.facilittecnologia.controller.response.TarefaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Documentação openAPI", description = "gerenciador de tarefas, " +
        "permitindo criar, visualizar e atualizar tarefas.")
public interface TarefaDocumentacaoOpenAPI {

    @Operation(summary = "Buscar todas tarefas no banco de dados.",
            description = "Retorna uma lista de JSON com todas tarefas registrada " +
                    "no banco de dados ou uma lista vazia[ ] caso não contenha nenhuma tarefa.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TarefaResponseDTO.class))
                            )
                    ),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<List<TarefaResponseDTO>> buscarTodas();


    @Operation(summary = "Salvar um tarefa no banco de dados.",
            description = "Retorna uma JSON de tarefa após criada com todos os campos válidos.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "201",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TarefaResponseDTO.class)))

                    , @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            })
    ResponseEntity<TarefaResponseDTO> salvar(TarefaRequestDTO tarefaRequestDTO);

}
