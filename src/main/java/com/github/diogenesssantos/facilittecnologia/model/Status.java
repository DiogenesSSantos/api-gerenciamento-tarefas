package com.github.diogenesssantos.facilittecnologia.model;

import com.github.diogenesssantos.facilittecnologia.exception.CampoInvalidoException;

public enum Status {
    FAZER("A fazer"),
    PROGRESSO("Em progresso"),
    ATRASADO("Em atraso"),
    CONCLUIDO("Concluido")
    ;

    private String nome;


    Status(String nome) {
        this.nome = nome;
    }


    public static void validoOuThrows(Status status) {
        if (status == null) throw new CampoInvalidoException(
                "O campo status deve conter um Status (FAZER, PROGRESSO, ATRASADO, CONCLUIDO)", "status");
    }


}
