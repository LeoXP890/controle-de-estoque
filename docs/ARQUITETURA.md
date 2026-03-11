# Arquitetura do Projeto

Este documento descreve a arquitetura adotada para o sistema de Controle de Estoque.

## Visão geral

O projeto é dividido em duas aplicações:

- Frontend SPA em Vue.js
- Backend API REST em Spring Boot

Comunicação entre as partes:

- Protocolo HTTP
- Dados em JSON
- Autenticação via JWT no header `Authorization`

## Diagrama lógico (alto nível)

1. Usuário acessa a SPA no navegador.
2. SPA chama `POST /auth/login` no backend.
3. Backend valida credenciais e emite JWT.
4. SPA armazena token e usa em chamadas protegidas.
5. Backend valida token e role em cada requisição.
6. Serviços persistem dados via JPA no banco.

## Frontend (Vue.js)

### Responsabilidades

- Renderizar telas
- Controlar navegação
- Gerenciar sessão (token/role)
- Consumir API REST
- Restringir ações de UI por papel

### Estrutura principal

- `src/router/index.js`
  - Rotas da SPA e guarda de autenticação
- `src/services/api.js`
  - Instância Axios com interceptor de token
- `src/services/auth.js`
  - Funções de sessão no `localStorage`
- `src/views/*`
  - Telas Login, Dashboard, Produtos e Categorias

### Proteção de rotas no frontend

- Rotas privadas exigem token
- Sem token, redireciona para `/login`
- Tela de login redireciona para dashboard quando já autenticado

## Backend (Spring Boot)

### Camadas

- `controller`
  - Endpoints REST, validações de entrada
- `service`
  - Regras de negócio
- `repository`
  - Persistência via Spring Data JPA
- `model`
  - Entidades (`User`, `Categoria`, `Produto`)
- `dto`
  - Contratos de request/response
- `security/config`
  - JWT, filtro, configuração do Spring Security

### Segurança

- Login em `/auth/login`
- Filtro JWT intercepta chamadas protegidas
- SecurityConfig aplica regras por método HTTP e endpoint
  - GET para `ADMIN` e `USER`
  - POST/PUT/DELETE apenas para `ADMIN`

## Modelo de dados

### `User`

- `id`
- `username`
- `password`
- `role`

### `Categoria`

- `id`
- `nome`
- `descricao`

### `Produto`

- `id`
- `nome`
- `descricao`
- `quantidade`
- `preco`
- `categoria` (muitos para um)
- `dataCadastro`

## Perfis de execução

- `application.properties`
  - Configuração padrão com MySQL
- `application-local.properties`
  - Perfil local com H2 para execução sem banco externo

## Decisões de design

- Separação clara de responsabilidades por camada
- DTOs para evitar expor entidades diretamente
- JWT stateless para escalabilidade e simplicidade
- Guardas no frontend para melhorar UX
- Regras de autorização no backend como fonte final de verdade

## Melhorias futuras sugeridas

- Paginação e filtros nos list endpoints
- Testes automatizados (unit e integração)
- Refresh token e expiração renovável
- Observabilidade (logs estruturados e métricas)
- Docker Compose com backend + frontend + banco
