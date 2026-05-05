package com.github.diogenesssantos.facilittecnologia.exception;

public class TarefaNaoLocalizadaException extends RuntimeException{

    private String campo;

    public TarefaNaoLocalizadaException(String message, String campo) {
        super(message);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}
