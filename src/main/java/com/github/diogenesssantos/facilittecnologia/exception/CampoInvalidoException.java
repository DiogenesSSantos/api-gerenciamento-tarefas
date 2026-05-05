package com.github.diogenesssantos.facilittecnologia.exception;


public class CampoInvalidoException  extends  RuntimeException{

    private String campo;

    public CampoInvalidoException(String message, String campo) {
        super(message);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}
