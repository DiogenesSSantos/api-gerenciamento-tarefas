package com.github.diogenesssantos.facilittecnologia.model;

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
}
