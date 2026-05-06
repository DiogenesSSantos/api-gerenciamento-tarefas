package com.github.diogenesssantos.facilittecnologia.docs;

import com.github.diogenesssantos.facilittecnologia.controller.LoginController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "Login", description = "login, de usuários.")
public interface LoginDocumentacaoOpenAPI {


    @Operation(summary = "Login de usuário.",
            description = "Retorna um token bearer de acesso aos end-points de tarefa. " +
                    "se o usuário existe no banco de dados.",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = LoginRepresentacaoOpenAPI.REPRESENTACAO))

                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(example = LoginRepresentacaoOpenAPI.UNAUTHORIZED))

                    ),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            })
    ResponseEntity<?> login(@RequestBody(
            description = "login do usuário",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(example = LoginRepresentacaoOpenAPI.LOGIN)
            ))LoginController.AuthRequest req);
}
