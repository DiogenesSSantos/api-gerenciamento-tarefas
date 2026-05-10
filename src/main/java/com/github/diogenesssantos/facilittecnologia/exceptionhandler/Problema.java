package com.github.diogenesssantos.facilittecnologia.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Problema(
        int statusCode,
        String mensagem,
        String mensagemUsuario,
        String classException,
        LocalDateTime timeStamp,

        List<ErrorCampos> errorsCampos
) {

    record ErrorCampos(String campo, String mensagem ) {}


}
