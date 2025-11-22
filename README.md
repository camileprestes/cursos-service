# Microsserviço de Cursos - Sistema de E-learning Corporativo

Este projeto é o Microsserviço de Cursos, parte de um Sistema de E-learning Corporativo desenvolvido com arquitetura de microsserviços. Sua responsabilidade é gerenciar todas as informações relacionadas a cursos, categorias, módulos e materiais de apoio.

---

## Funcionalidades Principais

- **CRUD completo** para Cursos, Categorias e Módulos.
- **Upload de materiais** (PDFs, vídeos, etc.) para os módulos, com armazenamento em MinIO (S3).
- **Busca e filtragem avançada** de cursos por categoria, instrutor, nível e palavra-chave.
- **Publicação de eventos** (ex: `curso.criado`, `curso.atualizado`) no RabbitMQ para comunicação assíncrona com outros microsserviços.
- **API documentada** com Swagger/OpenAPI.
- **Validação de dados** de entrada e tratamento de erros padronizado.

---

## Tecnologias Utilizadas

- **Linguagem:** Java 17
- **Framework:** Spring Boot 3
- **Persistência:** Spring Data JPA (Hibernate)
- **Banco de Dados:** PostgreSQL 15
- **Mensageria:** RabbitMQ
- **Armazenamento de Arquivos:** MinIO (Compatível com S3)
- **Migrações de Banco:** Flyway
- **Containerização:** Docker e Docker Compose
- **Build:** Maven
- **Testes:** JUnit 5 e Mockito
- **Documentação da API:** Springdoc OpenAPI (Swagger)

---

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:

- JDK 17 ou superior
- Apache Maven 3.8+
- Docker e Docker Compose

---

## Como Rodar a Aplicação

Siga os passos abaixo para executar o microsserviço e toda a sua infraestrutura localmente.

### 1. Iniciar a Infraestrutura com Docker

Todos os serviços externos (banco de dados, mensageria e storage) são gerenciados pelo Docker Compose. Para iniciá-los, execute na raiz do projeto:

```bash
docker-compose up -d
```

Este comando irá iniciar os seguintes contêineres em background:

- **PostgreSQL:** `localhost:5432`
- **RabbitMQ:** `localhost:5672` (Painel: [http://localhost:15672](http://localhost:15672))
- **MinIO:** `localhost:9000` (Painel: [http://localhost:9001](http://localhost:9001))

### 2. Executar o Microsserviço Spring Boot

Após a infraestrutura estar no ar, inicie a aplicação Java com o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

O servidor estará disponível em [http://localhost:8080](http://localhost:8080).

---

## Documentação da API (Swagger)

Acesse a documentação interativa da API (Swagger UI):

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Lá você poderá ver todos os endpoints, seus parâmetros e testá-los diretamente pelo navegador.

---

## Endpoints da API

### Cursos (`/cursos`)

| Método | Endpoint                | Descrição                                              |
|--------|------------------------ |-------------------------------------------------------|
| POST   | `/cursos`               | Cria um novo curso.                                   |
| GET    | `/cursos`               | Lista todos os cursos, com suporte a filtros.         |
| GET    | `/cursos/{id}`          | Busca um curso pelo seu ID.                           |
| PUT    | `/cursos/{id}`          | Atualiza um curso existente.                          |
| DELETE | `/cursos/{id}`          | Deleta um curso.                                      |
| PATCH  | `/cursos/{id}/ativar`   | Ativa um curso, tornando-o visível no catálogo.       |
| PATCH  | `/cursos/{id}/desativar`| Desativa um curso.                                    |
| POST   | `/cursos/{id}/duplicar` | Cria uma cópia completa de um curso e seus módulos.   |

**Parâmetros de Filtro para `GET /cursos`:**

- `categoria` (String): Filtra por código da categoria (ex: TECH)
- `instrutorId` (Long): Filtra por ID do instrutor
- `nivel` (Enum): Filtra por nível de dificuldade (`INICIANTE`, `INTERMEDIARIO`, `AVANCADO`)
- `q` (String): Busca por palavra-chave no título ou descrição

---

### Categorias (`/categorias`)

| Método | Endpoint           | Descrição                          |
|--------|--------------------|-------------------------------------|
| POST   | `/categorias`      | Cria uma nova categoria.            |
| GET    | `/categorias`      | Lista todas as categorias.          |
| GET    | `/categorias/{id}` | Busca uma categoria pelo seu ID.    |
| PUT    | `/categorias/{id}` | Atualiza uma categoria.             |
| DELETE | `/categorias/{id}` | Deleta uma categoria.               |

---

### Módulos (`/cursos/{cursoId}/modulos` e `/modulos/{moduloId}`)

| Método | Endpoint                              | Descrição                          |
|--------|---------------------------------------|-------------------------------------|
| POST   | `/cursos/{cursoId}/modulos`           | Adiciona um novo módulo a um curso. |
| GET    | `/cursos/{cursoId}/modulos`           | Lista todos os módulos de um curso. |
| PUT    | `/modulos/{moduloId}`                 | Atualiza um módulo.                 |
| DELETE | `/modulos/{moduloId}`                 | Deleta um módulo.                   |

---

### Materiais (`/modulos/{moduloId}/materiais`)

| Método | Endpoint                              | Descrição                                   |
|--------|---------------------------------------|----------------------------------------------|
| POST   | `/modulos/{moduloId}/materiais`       | Faz upload de um arquivo de material.        |
| GET    | `/modulos/{moduloId}/materiais`       | Lista todos os materiais de um módulo.       |

---

### Instrutores (Mock) (`/instrutores-mock`)

| Método | Endpoint                | Descrição                                 |
|--------|-------------------------|--------------------------------------------|
| POST   | `/instrutores-mock`     | Cria um instrutor de teste (mock).         |
| GET    | `/instrutores-mock`     | Lista todos os instrutores de teste.        |

---

## Como Rodar os Testes

Para executar a suíte de testes de unidade e garantir que a lógica de negócio está funcionando corretamente, utilize:

```bash
./mvnw test
```