package com.github.diogenesssantos.facilittecnologia.exceptionhandller;

import com.github.diogenesssantos.facilittecnologia.exception.CampoInvalidoException;
import com.github.diogenesssantos.facilittecnologia.exception.TarefaNaoLocalizadaException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@RestControllerAdvice
public class ExceptionHandllerGlobal {


    @ExceptionHandler(TarefaNaoLocalizadaException.class)
    public ResponseEntity<Problema> TarefaNaoLocalizadaException(TarefaNaoLocalizadaException ex,
                                                                 WebRequest webRequest,
                                                                 HttpServletRequest httpServletRequest) {


        var problema = new Problema(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                String.format("O campo %s passado não correlaciona a uma tarefa no banco de dados.",
                        ex.getCampo()),
                TarefaNaoLocalizadaException.class.getSimpleName(),
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
    }

    @ExceptionHandler(CampoInvalidoException.class)
    public ResponseEntity<Problema> CampoInvalidoException(CampoInvalidoException ex,
                                                           WebRequest webRequest,
                                                           HttpServletRequest request) {

        var problema = new Problema(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                String.format("O campo %s é obrigatório e está inválido.", ex.getCampo()),
                CampoInvalidoException.class.getSimpleName(),
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);


    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problema> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<Problema.ErrorCampos> errorCampos = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> new Problema.ErrorCampos(fe.getField(), fe.getDefaultMessage()))
                .toList();

        var problema = new Problema(
                HttpStatus.BAD_REQUEST.value(),
                "Campos invalidos.",
                "Um ou mais campos estão invalidos.",
                MethodArgumentNotValidException.class.getSimpleName(),
                LocalDateTime.now(),
                errorCampos);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);

    }


}
