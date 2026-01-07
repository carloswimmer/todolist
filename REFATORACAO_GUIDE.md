# Guia de Refatoração - Todolist Java Spring

Este guia servirá como roteiro para transformar sua aplicação atual em uma arquitetura robusta e escalável, dividida em "aulas" práticas para o seu aprendizado.

---

## Aula 1: Gestão de Erros Profissional
**Objetivo:** Centralizar o tratamento de erros e usar exceções que façam sentido para o negócio.

1.  **Criar Exceções de Domínio:**
    *   Crie o pacote `com.carloswimmer.todolist.exceptions`.
    *   Crie uma classe `BusinessException` que estende `RuntimeException`.
    *   Crie sub-exceções como `UserAlreadyExistsException` ou `InvalidTaskDateException`.

2.  **Refatorar o Global Handler:**
    *   No `ExceptionHandlerController`, adicione métodos `@ExceptionHandler` para capturar essas novas exceções.
    *   Retorne o `ErrorResponse` com o status HTTP apropriado (ex: 400 para BusinessException, 404 para Not Found).

---

## Aula 2: Camada de Serviço (Service Layer)
**Objetivo:** Isolar a lógica de negócio e garantir que os Controllers sejam "magros".

1.  **Criar os Services:**
    *   `UserService`: Deve conter o método `create`, lidando com a verificação de duplicidade e o hashing da senha.
    *   `TaskService`: Deve conter métodos para `create`, `update` e `list`. Mova as validações de `LocalDateTime` para cá.

2.  **Injeção de Dependência:**
    *   Substitua a injeção do `Repository` no `Controller` pela injeção do `Service`.

---

## Aula 3: Desacoplando o Banco de Dados (Repository Pattern)
**Objetivo:** Preparar a aplicação para trocar de banco de dados (ex: de H2/JPA para MongoDB ou JDBC puro) sem tocar na lógica de negócio.

1.  **Definir Interfaces de Domínio:**
    *   Crie interfaces "puras" (ex: `IUserRepository`) que NÃO estendam `JpaRepository`. Elas devem definir apenas os métodos que o seu sistema precisa.

2.  **Implementação de Infraestrutura:**
    *   Crie uma classe que implemente sua interface de domínio e que, internamente, utilize o Spring Data JPA para realizar as operações.

3.  **Inversão de Dependência:**
    *   O `Service` deve depender da Interface de Domínio, nunca da implementação específica do JPA.

---

## Aula 4: Autenticação Limpa (Middleware e AuthService)
**Objetivo:** Retirar a complexidade de segurança de dentro dos filtros e centralizar a lógica de autenticação.

1.  **AuthService:**
    *   Crie um serviço focado apenas em validar credenciais (username/password) e retornar o usuário autenticado.

2.  **Refatorar o Filtro:**
    *   O `FilterTaskAuth` deve apenas extrair os dados do header `Authorization` e chamar o `AuthService`. Se a autenticação falhar, o filtro lança o erro.

---

## Aula 5: DTOs (Data Transfer Objects) e Segurança
**Objetivo:** Proteger seus modelos de dados e controlar exatamente o que entra e sai da API.

1.  **Request/Response DTOs:**
    *   Crie classes para representar os dados de entrada (ex: `UserRequestDTO`) e os dados de saída.
    *   Nunca retorne a entidade `UserModel` diretamente, pois ela pode conter o campo de senha.

2.  **Mapeamento:**
    *   Converta DTOs para Modelos no Service antes de salvar, e Modelos para DTOs antes de retornar ao Controller.

