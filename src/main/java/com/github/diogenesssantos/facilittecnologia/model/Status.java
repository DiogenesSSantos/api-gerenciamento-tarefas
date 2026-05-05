package com.github.diogenesssantos.facilittecnologia.model;

import com.github.diogenesssantos.facilittecnologia.exception.CampoInvalidoException;

public enum Status {
    FAZER("A fazer"),
    PROGRESSO("Em progresso"),
    ATRASADO("Em atraso"),
    CONCLUIDO("Concluido")
    ;




    Status(String nome) {
        this.nome = nome;
    }

    private String nome;

    public static void validoOuThrows(Status status) {
        if (status == null) throw new CampoInvalidoException(
                "O campo status deve conter um Status (FAZER, PROGRESSO, ATRASADO, CONCLUIDO)", "status");
    }


}
