package com.github.diogenesssantos.facilittecnologia.docs;

public interface TarefaRepresentacaoOpenAPI {

    String BAD_REQUEST_400 = """         
            {
                "statusCode": 400,
                "mensagem": "Campo obrigatória está null ou vázio",
                "mensagemUsuario": "Campo obrigatório está inválido.",
                "classeException": "IllegalArgumentException",
                "timeStamp": "2026-05-07T10:42:19.0832846"
            }
            
            """;

    String NOT_FOUND_404 = """
            
            {
                "statusCode": 404,
                "mensagem": "Tarefa de id [?] não localizado no banco de dados.",
                "mensagemUsuario": "O id passado não correlaciona a uma tarefa no banco de dados, passe um id válido.",
                "classeException": "TarefaNotFoundException",
                "timeStamp": "2026-05-07T11:27:45.2638271"
            }
            
            """;

    String NOT_FOUND_TITULO_404 = """
            
            {
                "statusCode": 404,
                "mensagem": "Tarefa de titulo [?] não localizado no banco de dados.",
                "mensagemUsuario": "O titulo passado não correlaciona no banco de dados, passe um titulo válido.",
                "classeException": "TarefaNotFoundException",
                "timeStamp": "2026-05-07T11:27:45.2638271"
            }
            
            """;

    String NOT_FOUND_DESCRICAO_404 = """
            
            {
                "statusCode": 404,
                "mensagem": "Tarefa de descrição [?] não localizado no banco de dados.",
                "mensagemUsuario": "A descrição passada não correlaciona a uma tarefa no banco de dados, passe um descrição válido.",
                "classeException": "TarefaNotFoundException",
                "timeStamp": "2026-05-07T11:27:45.2638271"
            }
            
            """;

    String NOT_CONTENT = "";


}
