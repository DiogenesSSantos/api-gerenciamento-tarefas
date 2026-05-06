#  Gerenciador tarefas
Um serviço simples e eficiente de gerenciamento de tarefas, construído com tecnologias modernas para oferecer
desempenho e escalabilidade.

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
O gerencia tarefas de forma eficiente, possibilitando organização dos seus afazeres diários.


## 🛠 Tecnologias Utilizadas
- **Java 21** → Linguagem consolidada no mercado.  
  👉 [Documentação oficial](https://docs.oracle.com/en/java/)
- **Spring-Framework + Spring boot** →  framework open‑source robusto para a plataforma Java.

  👉 [Documentação oficial](https://spring.io/why-spring)

- **Lombok** → Elimina código boilerplate, como getters, setters e construtores.  
  👉 [Documentação oficial](https://projectlombok.org/)

- **Swagger** → Para documentação de api um UI-UX.  
  👉 [Documentação oficial](https://springdoc.org)

- **Docker** → Containerização para um deploy simples e consistente.  
  👉 [Documentação oficial](https://docs.docker.com/)

- **Copilot** → inteligência artificial:
    👉 [Documentação oficial](https://learn.microsoft.com/pt-br/copilot/?utm_source=copilot.com/)

---

## ✨ Funcionalidades
- **Gerenciar tarefas:** Controle total para criar, alterar e consultar tarefas.
- **Persistência com H2:** Armazena as tarefas no banco de dados H2 em memória.
- **Suporte a Docker:** Facilita a execução em qualquer ambiente.

---

## 📐 Arquitetura do Projeto - !!! em construção.
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
     └── java/
         └── br/com/expresscart/
             └── (testes unitários e de integração)
```

---

## 🚀 Como Rodar o Projeto

### 🔧 Pré-requisitos
- **Docker** 
- **Java 21+**
- **Maven** (ou utilize `mvnw`)

### ▶️ Rodando a aplicação

```
docker run -p 8080:8080 diogenesssantos/facilittecnologia:1.0
```
### Como acessar
1. Certifique-se de que a aplicação está rodando (após seguir os passos em ["Como Rodar o Projeto"](#-como-rodar-o-projeto)).
2. Abra seu navegador e navegue até a seguinte URL:

👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

3. Fazendo requisição utilizando uma ferramenta de desenvolvimento (Postman, insomia, etc...) copie o link abaixo.

👉 [http://localhost:8080/](http://localhost:8080) e o determinado end-point abaixo.


> **⚠️ Atenção**
>
> Obrigatório fazer o login para acessar os end-points, necessitando do token JWT para acesso aos end-points.
---

**Faça um post http://localhost:8080/api/auth/login com o body abaixo, depois copie o JWT e faça requisições.**
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
## 📚 Documentação da API
A documentação interativa da API foi gerada com **Swagger** e pode ser acessada após a inicialização da aplicação.

Ela permite que você **visualize e teste todos os endpoints disponíveis**.


### O que você pode fazer na documentação

- **Visualizar Endpoints:** Veja todos os endpoints da API, com seus métodos (GET, POST, PATCH), parâmetros e descrições.
- **Testar Requisições:** Use a funcionalidade *Try it out* para enviar requisições e ver as respostas em tempo real.
- **Consultar Schemas:** Entenda a estrutura das entidades (DTOs) utilizadas nas requisições e respostas.  