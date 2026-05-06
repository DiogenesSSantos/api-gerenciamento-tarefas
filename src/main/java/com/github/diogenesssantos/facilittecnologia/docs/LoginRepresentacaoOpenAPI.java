package com.github.diogenesssantos.facilittecnologia.docs;

public interface LoginRepresentacaoOpenAPI {


    String REPRESENTACAO = """
            
            {
                "nome": "facilit",
                "token": "dsad1233214fsafasfasfaffasfafa12123214..."
            
            }
            
            """;


    String UNAUTHORIZED = """
            
            {
                "error": "Não autorizado"
            
            }
            
            """;
    String LOGIN = """
            
            {
                "nome": "facilit",
                "senha": "123"
            
            }
            
            """;
}
