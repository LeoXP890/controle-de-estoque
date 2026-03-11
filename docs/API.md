# Documentação da API

Este documento descreve os endpoints REST do sistema de Controle de Estoque, incluindo autenticação, autorização e exemplos de uso.

## Base URL

- Backend local: `http://localhost:8080`

## Autenticação

A autenticação é baseada em JWT.

- Endpoint público de login: `POST /auth/login`
- Rotas protegidas exigem header:

```http
Authorization: Bearer <token>
```

## Papéis e permissões

- `ADMIN`
  - Pode executar GET, POST, PUT e DELETE em produtos e categorias
- `USER`
  - Pode executar apenas GET em produtos e categorias

## Endpoints

## 1) Login

### `POST /auth/login`

**Request**

```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Response 200**

```json
{
  "token": "<jwt>",
  "role": "ADMIN",
  "username": "admin"
}
```

**Possíveis respostas**

- `200 OK`: login válido
- `401 Unauthorized`: credenciais inválidas

## 2) Categorias

### `GET /categorias`

- Autorização: `ADMIN`, `USER`
- Retorna lista de categorias

**Response 200**

```json
[
  {
    "id": 1,
    "nome": "Eletrônicos",
    "descricao": "Itens eletrônicos"
  }
]
```

### `GET /categorias/{id}`

- Autorização: `ADMIN`, `USER`
- Retorna uma categoria por ID

### `POST /categorias`

- Autorização: `ADMIN`

**Request**

```json
{
  "nome": "Periféricos",
  "descricao": "Acessórios de computador"
}
```

**Response 200**

```json
{
  "id": 2,
  "nome": "Periféricos",
  "descricao": "Acessórios de computador"
}
```

### `PUT /categorias/{id}`

- Autorização: `ADMIN`
- Atualiza categoria existente

### `DELETE /categorias/{id}`

- Autorização: `ADMIN`
- Remove categoria
- Retorno: `204 No Content`

## 3) Produtos

### `GET /produtos`

- Autorização: `ADMIN`, `USER`
- Retorna lista de produtos

**Response 200**

```json
[
  {
    "id": 1,
    "nome": "Mouse",
    "descricao": "Mouse sem fio",
    "quantidade": 50,
    "preco": 79.9,
    "categoriaId": 1,
    "categoriaNome": "Periféricos",
    "dataCadastro": "2026-03-11T20:00:00"
  }
]
```

### `GET /produtos/{id}`

- Autorização: `ADMIN`, `USER`
- Retorna produto por ID

### `POST /produtos`

- Autorização: `ADMIN`

**Request**

```json
{
  "nome": "Teclado",
  "descricao": "Teclado mecânico",
  "quantidade": 20,
  "preco": 199.9,
  "categoriaId": 1
}
```

### `PUT /produtos/{id}`

- Autorização: `ADMIN`
- Atualiza produto existente

### `DELETE /produtos/{id}`

- Autorização: `ADMIN`
- Remove produto
- Retorno: `204 No Content`

## Códigos de status esperados

- `200 OK`: sucesso em GET/POST/PUT
- `204 No Content`: sucesso em DELETE
- `400 Bad Request`: payload inválido
- `401 Unauthorized`: sem token/credenciais inválidas
- `403 Forbidden`: token válido sem permissão para ação
- `404 Not Found`: recurso não encontrado

## Exemplo de fluxo completo

1. Fazer login em `POST /auth/login`.
2. Copiar o token retornado.
3. Enviar token no header `Authorization`.
4. Consumir endpoints de categorias e produtos conforme papel do usuário.
