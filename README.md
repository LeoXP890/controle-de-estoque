# Controle de Estoque

Aplicação fullstack para gerenciamento de produtos e categorias, com autenticação JWT, autorização por papéis e integração completa entre SPA (Vue.js) e API REST (Spring Boot).

## 1. Objetivo

Demonstrar, de ponta a ponta:

- SPA com Vue.js
- API REST com Spring Boot
- CRUD completo de Produtos e Categorias
- Autenticação com JWT
- Autorização por papéis (`ADMIN` e `USER`)
- Proteção de rotas no backend e frontend
- Persistência em banco de dados

## 2. Stack Tecnológica

### Frontend

- Vue 3
- Vue Router
- Axios
- Vite

### Backend

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT (`jjwt`)
- Bean Validation

### Banco de dados

- MySQL (produção/local tradicional)
- PostgreSQL (alternativo)
- H2 (perfil local de teste rápido)

## 3. Arquitetura

### Frontend (SPA)

- Rotas: `/login`, `/dashboard`, `/produtos`, `/categorias`
- Guardas de rota verificam autenticação
- Token JWT salvo no `localStorage`
- Axios injeta `Authorization: Bearer <token>` em requisições protegidas

### Backend (API REST)

Organização em camadas:

- `controller`: entrada HTTP
- `service`: regras de negócio
- `repository`: acesso ao banco
- `model/entity`: entidades JPA
- `dto`: contratos de entrada/saída
- `security` e `config`: JWT, filtros e autorização

## 4. Estrutura de Pastas

```text
.
├─ backend/
│  ├─ pom.xml
│  └─ src/main/
│     ├─ java/com/estoque/api/
│     │  ├─ config/
│     │  ├─ controller/
│     │  ├─ dto/
│     │  ├─ model/
│     │  ├─ repository/
│     │  ├─ security/
│     │  └─ service/
│     └─ resources/
│        ├─ application.properties
│        └─ application-local.properties
└─ frontend/
    ├─ package.json
    └─ src/
         ├─ router/
         ├─ services/
         └─ views/
```

## 5. Modelo de Dados

### Entidade `User`

- `id`
- `username`
- `password`
- `role` (`ADMIN` | `USER`)

### Entidade `Categoria`

- `id`
- `nome`
- `descricao`

### Entidade `Produto`

- `id`
- `nome`
- `descricao`
- `quantidade`
- `preco`
- `categoria` (N:1)
- `dataCadastro`

### Relacionamento

- Uma `Categoria` possui vários `Produto`s.

## 6. Autenticação e Autorização

### Login JWT

- Endpoint: `POST /auth/login`
- Backend valida usuário/senha
- Retorna token JWT + dados do usuário

### Papéis do sistema

- `ADMIN`
   - Pode criar, editar, listar e excluir produtos e categorias
   - Acesso completo às telas e ações
- `USER`
   - Pode apenas visualizar produtos e categorias
   - Não pode criar, editar ou excluir

### Regras de acesso na API

- `GET /produtos/**` e `GET /categorias/**`: `ADMIN` e `USER`
- `POST`, `PUT`, `DELETE` de produtos/categorias: apenas `ADMIN`
- Demais rotas: autenticadas

## 7. Endpoints REST

### Autenticação

- `POST /auth/login`

**Request**

```json
{
   "username": "admin",
   "password": "admin123"
}
```

**Response**

```json
{
   "token": "<jwt>",
   "role": "ADMIN",
   "username": "admin"
}
```

### Categorias

- `GET /categorias`
- `GET /categorias/{id}`
- `POST /categorias`
- `PUT /categorias/{id}`
- `DELETE /categorias/{id}`

**Request (POST/PUT)**

```json
{
   "nome": "Eletrônicos",
   "descricao": "Itens eletrônicos"
}
```

### Produtos

- `GET /produtos`
- `GET /produtos/{id}`
- `POST /produtos`
- `PUT /produtos/{id}`
- `DELETE /produtos/{id}`

**Request (POST/PUT)**

```json
{
   "nome": "Mouse",
   "descricao": "Mouse sem fio",
   "quantidade": 50,
   "preco": 79.9,
   "categoriaId": 1
}
```

## 8. Frontend: Telas e Fluxo

### 1) Login

- Campos de usuário e senha
- Chama `POST /auth/login`
- Salva token/role/username no `localStorage`
- Redireciona para `/dashboard`

### 2) Dashboard

- Exibe totais de produtos e categorias
- Navegação para telas de gestão

### 3) Produtos

- Lista produtos
- `ADMIN`: formulário + botões de editar/excluir
- `USER`: somente visualização

### 4) Categorias

- Lista categorias
- `ADMIN`: formulário + botões de editar/excluir
- `USER`: somente visualização

## 9. Como Executar

## Pré-requisitos

- Java 17+
- Maven 3.9+
- Node.js 18+
- npm 9+

### Opção A: com MySQL (padrão)

1. Configure credenciais em `backend/src/main/resources/application.properties`.
2. Suba o backend:

```bash
cd backend
mvn spring-boot:run
```

3. Suba o frontend:

```bash
cd frontend
npm install
npm run dev
```

### Opção B: perfil local com H2 (sem MySQL/PostgreSQL)

```bash
cd backend
mvn spring-boot:run "-Dspring-boot.run.profiles=local"
```

- API: `http://localhost:8080`
- Console H2: `http://localhost:8080/h2-console`

Frontend (mesmo comando):

```bash
cd frontend
npm install
npm run dev
```

- SPA: `http://localhost:5173`

## 10. Credenciais Iniciais

Criadas automaticamente no backend:

- `ADMIN`: `admin` / `admin123`
- `USER`: `user` / `user123`

## 11. Exemplo de Teste com cURL

### Login

```bash
curl -X POST http://localhost:8080/auth/login \
   -H "Content-Type: application/json" \
   -d '{"username":"admin","password":"admin123"}'
```

### Listar produtos com token

```bash
curl http://localhost:8080/produtos \
   -H "Authorization: Bearer <jwt>"
```

## 12. Fluxo Funcional Esperado

1. Usuário acessa a SPA.
2. Faz login e recebe JWT.
3. Frontend salva token e envia no header `Authorization`.
4. Backend valida token e role em cada requisição protegida.
5. Operações de CRUD são persistidas no banco.

## 13. Observações

- Se `8080` estiver em uso, finalize o processo da porta antes de subir o backend.
- Sem token JWT, rotas protegidas retornam erro de autenticação/autorização (comportamento esperado).

## 14. Documentação complementar

- API detalhada: [docs/API.md](docs/API.md)
- Arquitetura: [docs/ARQUITETURA.md](docs/ARQUITETURA.md)
