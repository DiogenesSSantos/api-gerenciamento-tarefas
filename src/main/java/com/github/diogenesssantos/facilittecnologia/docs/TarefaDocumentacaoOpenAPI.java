package com.github.diogenesssantos.facilittecnologia.docs;


import com.github.diogenesssantos.facilittecnologia.controller.request.TarefaRequestDTO;
import com.github.diogenesssantos.facilittecnologia.controller.response.TarefaResponseDTO;
import com.github.diogenesssantos.facilittecnologia.model.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Tarefas", description = "permitindo criar, visualizar e atualizar tarefas.")
public interface TarefaDocumentacaoOpenAPI {


    @Operation(summary = "Buscar todas tarefas no banco de dados.",
            description = "Retorna uma lista de JSON com todas tarefas registrada " +
                    "no banco de dados ou uma lista vazia[ ] caso não contenha nenhuma tarefa.",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TarefaResponseDTO.class))
                            )
                    ),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            })
    ResponseEntity<List<TarefaResponseDTO>> buscarTodas();


    @Operation(summary = "Salvar uma tarefa no banco de dados.",
            description = "Retorna uma JSON de tarefa após criada com todos os campos válidos.",
            responses = {
                    @ApiResponse(description = "Create", responseCode = "201",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TarefaResponseDTO.class)))
                    ,
                    @ApiResponse(description = "Bad request", responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = TarefaRepresentacaoOpenAPI.BAD_REQUEST_400_TITULO)))
                    , @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            })
    ResponseEntity<TarefaResponseDTO> salvar(TarefaRequestDTO tarefaReqDTO);


    @Operation(summary = "Buscar todas as tarefa no banco de dados pelo status.",
            description = "Retorna uma lista de JSON com todas tarefas " +
                    "no banco de dados dado o status correspondente " +
                    "ou uma lista vazia[ ] caso não contenha nenhuma tarefa.",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = TarefaResponseDTO.class))))
                    ,
                    @ApiResponse(description = "Bad request", responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = TarefaRepresentacaoOpenAPI.BAD_REQUEST_400_STATUS)))
                    , @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            })
    ResponseEntity<List<TarefaResponseDTO>> buscarTodasPorStatus(Status status);


    @Operation(summary = "Buscar a tarefa no banco de dados pelo id.",
            description = "Retorna um JSON com uma tarefas " +
                    "no banco de dados dado o descricao correspondente.",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = TarefaResponseDTO.class))))
                    ,
                    @ApiResponse(description = "Not found", responseCode = "404",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = TarefaRepresentacaoOpenAPI.NOT_FOUND_404)))
                    , @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            })
    ResponseEntity<TarefaResponseDTO> buscarPorId(Long id);


    @Operation(summary = "Buscar a tarefa no banco de dados pela descrição.",
            description = "Retorna um JSON com uma tarefas " +
                    "no banco de dados dado o descricao correspondente.",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = TarefaResponseDTO.class))))
                    ,
                    @ApiResponse(description = "Not found", responseCode = "404",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = TarefaRepresentacaoOpenAPI.NOT_FOUND_DESCRICAO_404)))
                    , @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            })
    ResponseEntity<TarefaResponseDTO> buscarPorDescricao(String descricao);


    @Operation(summary = "Buscar a tarefa no banco de dados pela titulo.",
            description = "Retorna um JSON com uma tarefas " +
                    "no banco de dados dado o descricao correspondente..",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = TarefaResponseDTO.class))))
                    ,
                    @ApiResponse(description = "Not found", responseCode = "404",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = TarefaRepresentacaoOpenAPI.BAD_REQUEST_400_TITULO)))
                    , @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            })
    ResponseEntity<TarefaResponseDTO> buscarPorTitulo(String descricao);


    @Operation(summary = "Atualizar uma tarefa no banco de dados pelo id.",
            description = "Retorna uma JSON de tarefa após atualizar com campos válidos.",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TarefaResponseDTO.class)))
                    ,
                    @ApiResponse(description = "Bad request", responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = TarefaRepresentacaoOpenAPI.BAD_REQUEST_400_TITULO))),
                    @ApiResponse(description = "Not found", responseCode = "404",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = TarefaRepresentacaoOpenAPI.NOT_FOUND_404)))
                    , @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            })
    ResponseEntity<TarefaResponseDTO> atualizarPorId(Long id, TarefaRequestDTO tarefaReqDTO);


    @Operation(summary = "Atualizar status de uma tarefa no banco de dados pelo id.",
            description = "Retorna uma JSON de tarefa após atualizar seu status como o campo válido.",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TarefaResponseDTO.class)))
                    ,
                    @ApiResponse(description = "Bad request", responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = TarefaRepresentacaoOpenAPI.BAD_REQUEST_400_STATUS))),
                    @ApiResponse(description = "Not found", responseCode = "404",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = TarefaRepresentacaoOpenAPI.NOT_FOUND_404)))
                    , @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            })
    ResponseEntity<TarefaResponseDTO> atualizarStatusPorId(Long id, @RequestBody(
            description = "Atualiza a tarefa pelo id parcial ou por completo",
            required = true,
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"status\":\"PROGRESSO\"}")
            )) TarefaRequestDTO tarefaReqDTO);


    @Operation(summary = "Atualizar uma tarefa no banco de dados pela descricao.",
            description = "Retorna uma JSON de tarefa após atualizar pela descricao.",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TarefaResponseDTO.class)))
                    ,
                    @ApiResponse(description = "Bad request", responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = TarefaRepresentacaoOpenAPI.BAD_REQUEST_400_DESCRICAO))),
                    @ApiResponse(description = "Not found", responseCode = "404",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = TarefaRepresentacaoOpenAPI.NOT_FOUND_DESCRICAO_404)))
                    , @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            })
    ResponseEntity<TarefaResponseDTO> atualizarPorDescricao(String descricao, @RequestBody
            (description = "Atualiza a tarefa pela descricao parcial ou por completo.")
    TarefaRequestDTO tarefaReqDTO);


    @Operation(summary = "Atualizar uma tarefa no banco de dados pelo titulo.",
            description = "Retorna uma JSON de tarefa após atualizar pelo titulo.",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TarefaResponseDTO.class)))
                    ,
                    @ApiResponse(description = "Bad request", responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = TarefaRepresentacaoOpenAPI.BAD_REQUEST_400_TITULO))),
                    @ApiResponse(description = "Not found", responseCode = "404",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = TarefaRepresentacaoOpenAPI.NOT_FOUND_DESCRICAO_404)))
                    , @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            })
    ResponseEntity<TarefaResponseDTO> atualizarPorTitulo(String titulo, @RequestBody
            (description = "Atualiza a tarefa pelo titulo parcial ou por completo.")
    TarefaRequestDTO tarefaReqDTO);

}
