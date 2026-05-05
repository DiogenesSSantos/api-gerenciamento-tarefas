package com.github.diogenesssantos.facilittecnologia.docs;

public interface TarefaRepresentacaoOpenAPI {

    String BAD_REQUEST_400_TITULO = """         
           
            {
              "statusCode": 400,
              "mensagem": "O campo titulo não poder ser vázio",
              "mensagemUsuario": "O campo titulo é obrigatório e está inválido.",
              "classeException": "CampoInvalidoException",
              "timeStamp": "2026-05-04T21:20:57.8320543"
            }
           
            """;

    String BAD_REQUEST_400_DESCRICAO = """
            
            {
              "statusCode": 400,
              "mensagem": "O campo descrição não poder ser vázio",
              "mensagemUsuario": "O campo descricao é obrigatório e está inválido.",
              "classeException": "CampoInvalidoException",
              "timeStamp": "2026-05-04T21:21:27.5134085"
            }
            
            """;

    String BAD_REQUEST_400_RESPONSAVEL = """
            
             {
              "statusCode": 400,
              "mensagem": "O campo responsável não poder ser vázio",
              "mensagemUsuario": "O campo responsavel é obrigatório e está inválido.",
              "classeException": "CampoInvalidoException",
              "timeStamp": "2026-05-04T21:21:50.4528263"
            }
            
            """;

    String BAD_REQUEST_400_STATUS = """
            
            {
              "statusCode": 400,
              "mensagem": "O campo status deve conter um Status (FAZER, PROGRESSO, ATRASADO, CONCLUIDO)",
              "mensagemUsuario": "O campo status é obrigatório e está inválido.",
              "classeException": "CampoInvalidoException",
              "timeStamp": "2026-05-04T21:22:16.4049368"
            }
            
            """;

    String BAD_REQUEST_400_DATA_LIMITE = """
            
            {
              "statusCode": 400,
              "mensagem": "O campo dataLimite deve ser em um periodo no tempo futuro.",
              "mensagemUsuario": "O campo dataLimite é obrigatório e está inválido.",
              "classeException": "CampoInvalidoException",
              "timeStamp": "2026-05-04T21:17:08.9443613"
            }

            """;

    String NOT_FOUND_404 = """
            
            {
              "statusCode": 404,
              "mensagem": "A tarefa com o id [?] não existe no banco de dados.",
              "mensagemUsuario": "O campo id passado não correlaciona a uma tarefa no banco de dados.",
              "classeException": "TarefaNaoLocalizadaException",
              "timeStamp": "2026-05-05T06:02:05.8719807"
            }
            
            """;

    String NOT_FOUND_TITULO_404 = """
            
            {
                "statusCode": 404,
                "mensagem": "Tarefa de titulo [?] não localizado no banco de dados.",
                "mensagemUsuario": "O titulo passado não correlaciona a uma tarefa no banco de dados.",
                "classeException": "TarefaNotFoundException",
                "timeStamp": "2026-05-07T11:27:45.2638271"
            }
            
            """;

    String NOT_FOUND_DESCRICAO_404 = """
            
            {
                "statusCode": 404,
                "mensagem": "Tarefa de descrição [?] não localizado no banco de dados.",
                "mensagemUsuario": "A descrição passada não correlaciona a uma tarefa no banco de dados.",
                "classeException": "TarefaNotFoundException",
                "timeStamp": "2026-05-07T11:27:45.2638271"
            }
            
            """;

    String NOT_CONTENT = "";


}
