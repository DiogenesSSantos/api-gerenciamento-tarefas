#  Gerenciador de Tarefas — Facilittecnologia
Um serviço REST simples para criar, editar, buscar e gerenciar o status de tarefas, com autenticação JWT, documentação Swagger e persistência em H2..

---

## 🔗 Sumário
- [📖 Sobre o Projeto](#-sobre-o-projeto)
- [🛠 Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [✨ Funcionalidades](#-funcionalidades)
- [📐 Arquitetura do Projeto](#-arquitetura-do-projeto)
- [🚀 Como Rodar o Projeto](#-como-rodar-o-projeto)
- [📚 Documentação da API](#-documentação-da-api)

---

## 📖 Sobre o Projeto
Aplicação backend para gerenciamento de tarefas (To‑Do). Permite criar tarefas, editar campos, alterar status, buscar por texto e filtrar por status. Pensada para ser simples, testável e documentada.

## ✨ Funcionalidades
- **Listar tarefa**
- **Criar tarefa**
- **Editar tarefa**
- **Alterar status**
- **Filtro por status e busca por texto**
- **Persistência**
- **Autenticação**
- **Documentação Swagger**

## 🛠 Tecnologias Utilizadas
- **Java 21** → Linguagem consolidada no mercado.  
  👉 [Documentação oficial](https://docs.oracle.com/en/java/)
- **Spring-Framework + Spring boot** →  framework open‑source robusto para a plataforma Java.

  👉 [Documentação oficial](https://spring.io/why-spring)

- **Lombok** → Elimina código boilerplate, como getters, setters e construtores.  
  👉 [Documentação oficial](https://projectlombok.org/)-

- **JUnit + Mockito** → Banco de dados em memória.  
  👉 [Documentação oficial](https://site.mockito.org/)

- **H2 - Data base** → Banco de dados em memória.  
  👉 [Documentação oficial](https://www.h2database.com/html/main.html)
- **Swagger** → Para documentação de api um UI-UX.  
  👉 [Documentação oficial](https://springdoc.org)

- **Docker** → Containerização para um deploy simples e consistente.  
  👉 [Documentação oficial](https://docs.docker.com/)

- **Copilot** → inteligência artificial auxiliando no desenvolvimento (teste) 
  👉 [Documentação oficial](https://learn.microsoft.com/pt-br/copilot/?utm_source=copilot.com/)

---



---

## 📐 Arquitetura do Projeto - em construção.
```tree
src/
 ├── main/
 │   ├── java/
 │   │   └── com/github/diogenesssantos/facilittecnologia
 │   │       ├── assembler/
 │   │       |   |──AssemblerTarefa
 │   │       |
 │   │      
 │   │
 │   └── resources/
 │       ├── application.yml
 │       └── (outros arquivos de configuração, mapeamentos, etc.)
 │
 └── test/
     └── java/....
```

---

## 🚀 Como Rodar o Projeto

### 🔧 Pré-requisitos
- **Docker** 
- **Java 21+**
- **Maven** (ou utilize `mvnw`)

### ▶️ Rodando a aplicação



**docker**
```
docker run -p 8080:8080 diogenesssantos/facilittecnologia:1.0
```

**maven**
```
./mvnw clean package
java -jar target/facilittecnologia-1.0.jar

```
**docker-compose**
```
version: '3.8'
services:
  app:
    image: diogenesssantos/facilittecnologia:1.0
    ports:
      - "8080:8080"
 
```

**test**
- **Unitários: serviços e utilitários com Mockito.**
- **Integração: testes com SpringBootTest usando H2 em memória**

***comandos***

```
./mvnw test
```


### Como acessar
1. Certifique-se de que a aplicação está rodando (após seguir os passos em ["Como Rodar o Projeto"](#-como-rodar-o-projeto)).
2. Abra seu navegador e navegue até a seguinte URL:

👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

3. Fazendo requisição utilizando uma ferramenta de desenvolvimento (Postman, Insomnia, etc...) copie o link abaixo.

👉 [http://localhost:8080/](http://localhost:8080) e o determinado end-point abaixo.


> **⚠️ Atenção**
>
> Obrigatório fazer o login para acessar os end-points, necessitando do token JWT para acesso aos end-points.
---

**Faça uma requisição post http://localhost:8080/api/auth/login com o body abaixo, depois copie o JWT e 
faça requisições no end-points /tarefas.**
```
{
  "nome": "facilit",
  "senha": "123"
}
```

## ✨ End-Points

| Método | Nome do endpoint     | Descrição                                                                           |
|--------|----------------------|-------------------------------------------------------------------------------------|
| POST   | /api/auth/login      | fazer o login na api, obrigatório para todos end-points o token JWT.                | 
| POST   | /tarefas             | Cria uma nova tarefa.                                                               |
| GET    | /tarefas             | Buscar todas tarefas registradas.                                                   | 
| GET    | /tarefas/id/{id}     | Consulte informações de uma tarefa através de um``id`` .                            | 
| GET    | /tarefas/titulo      | Consulte informações de uma tarefa através de um``titulo``.                         |  
| GET    | /tarefas/descricao   | Consulte informações de uma tarefa através de uma``descricao``.                     |  
| GET    | /tarefas/status      | Buscar todas tarefas registrados por um ``status``.                                 |  
| PATCH  | /tarefas/id/{id}     | Atualiza uma ou mais informação de uma determinada tarefa  pelo ``id``.             | 
| PATCH  | /tarefas/titulo      | Atualiza uma ou mais informação de uma determinada tarefa  pelo``titulo``.          | 
| PATCH  | /tarefas/descricao   | Atualiza uma ou mais informação de uma determinada tarefa  pela``descricao``.       | 
| PATCH  | /tarefas/status/{id} | Atualiza campo status uma determinada tarefa pelo``id``, passando status atualizado.| 

---
##  Decisões técnicas
- **Spring Boot por produtividade e ecossistema.**
- **H2 para o desafio; mas fácil troca para SQLite/Postgres ou mysql(roda os teste em um mysql com test-container).**
- **JWT para autenticação stateless e compatibilidade com front-ends.**
- **Lombok para reduzir boilerplate.**

---
##  Limitações conhecidas
- **H2 em memória não é adequado para produção; dados são voláteis.**
- **Autenticação simples (usuário fixo) — não há cadastro/recuperação de senha.**
- **Sem controle de permissões por usuário (qualquer usuário tem acesso a todas as tarefas, correto cada um visualizar a sua respectiva tarefa).**

---

##  Utilização de IA (Copilot) no projeto ##


- Configuração de ambiente, sem comprometer dados sensíveis. 
- Criação de casos de teste.
- Autocomplete e geração de código para teste com rapidez sem substituir a análise critica e testabilidade.
- Leitura de problemas de stack trace, complexas para melhor entendimento e resolucionar o problema.
- Refatoração e melhoria de legibilidade


---



##  Melhorias
- **Banco de dados mais seguro, robusto e independente (Mysql ou Postgres).**
- **Endpoint para atribuir tarefa a usuário e histórico de mudanças e por quem foi alterada a tarefa.**
- **Autenticação para apenas administradores e cargo de liderança posso criar tarefas.**

---

##   Importar collection Postman a partir do OpenAPI 

```
Importar collection Postman a partir do OpenAPI

1. Inicie a API localmente.
2. No Postman: Import → Link → cole http://localhost:8080/v3/api-docs → Import.
3. Execute POST /api/auth/login, copie o token e cole em `jwt`.
4. Teste os endpoints protegidos com header Authorization: Bearer {{jwt}}.
5. Para importar manualmente, copie o arquivo da raiz do projeto docs/openapi e use Import → Upload Files.

```
---


Swagger para documentação interativa — facilita avaliação do desafio.
---
## 📚 Documentação da API
A documentação interativa da API foi gerada com **Swagger** e pode ser acessada após a inicialização da aplicação.

Ela permite que você **visualize e teste todos os endpoints disponíveis**.


### O que você pode fazer na documentação

- **Visualizar Endpoints:** Veja todos os endpoints da API, com seus métodos (GET, POST, PATCH), parâmetros e descrições.
- **Testar Requisições:** Use a funcionalidade *Try it out* para enviar requisições e ver as respostas em tempo real.
- **Consultar Schemas:** Entenda a estrutura das entidades (DTOs) utilizadas nas requisições e respostas.  