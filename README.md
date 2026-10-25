# API de Estoque

Uma API RESTful desenvolvida com Spring Boot para gerenciamento de produtos e controle de estoque. Esta aplicação fornece endpoints completos para operações CRUD (Create, Read, Update, Delete) em um banco de dados PostgreSQL, além de funcionalidades de movimentação de entrada e saída de estoque.

## 🚀 Funcionalidades

- **Gerenciamento de Produtos**: Operações completas de CRUD para cadastro de produtos
- **Gerenciamento de Estoques**: Operações completas de CRUD para controle de estoque
- **Movimentação de Estoque**: Registro de entradas e saídas com validação de quantidade disponível
- **Validação de Dados**: Validação automática de campos obrigatórios, valores positivos e tamanho de campos
- **Tratamento de Exceções**: Tratamento global de erros com mensagens claras
- **Nome Único**: Garantia de unicidade de nome no cadastro de produtos
- **Relacionamento 1:1**: Cada produto possui exatamente um estoque associado
- **Produto Ativo/Inativo**: Controle de status de produto para evitar movimentações de produtos inativos
- **Estoque Mínimo**: Configuração de estoque mínimo para alerta de reposição
- **Validação de Quantidade**: Não permite saída maior que o estoque disponível
- **DTOs**: Separação clara entre entidades de banco e objetos de transferência de dados
- **Testes Unitários**: Cobertura de testes para a camada de serviço com JUnit e Mockito
- **Paginação**: Listagem com suporte a paginação
- **Filtros Dinâmicos**: Consultas dinâmicas usando Specifications

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 4.0.8
- Spring Data JPA
- Spring Web MVC
- Spring Validation
- PostgreSQL
- Lombok
- Maven
- JUnit 5
- Mockito

## 📋 Pré-requisitos

- Java 17 ou superior instalado
- Maven 3.6+ instalado
- PostgreSQL 12+ instalado e rodando
- IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code, etc.)

## 🔧 Instalação e Configuração

### 1. Clone o repositório

```bash
git clone <repository-url>
cd API-de-Estoque
```

### 2. Configure o Banco de Dados

Crie um banco de dados PostgreSQL:

```sql
CREATE DATABASE api_estoque;
```

### 3. Configure as Credenciais

Edite o arquivo `src/main/resources/application.properties` ou configure as variáveis de ambiente:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/api_estoque
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

Ou usando variáveis de ambiente:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/api_estoque
export DB_USERNAME=postgres
export DB_PASSWORD=sua_senha
```

### 4. Compile o Projeto

```bash
./mvnw clean install
```

Ou usando Maven diretamente:

```bash
mvn clean install
```

## ▶️ Executando a Aplicação

### Via Maven

```bash
./mvnw spring-boot:run
```

### Via JAR

```bash
java -jar target/API-de-Estoque-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em: http://localhost:8080

## 📡 Endpoints da API

### Produtos

#### Criar Produto

```http
POST /produtos
Content-Type: application/json

{
  "nome": "Produto Exemplo",
  "descricao": "Descrição do produto exemplo",
  "preco": 99.99,
  "ativo": true
}
```

**Response: 201 Created**

```json
{
  "id": 1,
  "nome": "Produto Exemplo",
  "descricao": "Descrição do produto exemplo",
  "preco": 99.99,
  "ativo": true
}
```

#### Listar Todos os Produtos

```http
GET /produtos
```

**Response: 200 OK**

```json
[
  {
    "id": 1,
    "nome": "Produto Exemplo",
    "descricao": "Descrição do produto exemplo",
    "preco": 99.99,
    "ativo": true
  }
]
```

#### Buscar Produto por ID

```http
GET /produtos/{id}
```

**Response: 200 OK**

```json
{
  "id": 1,
  "nome": "Produto Exemplo",
  "descricao": "Descrição do produto exemplo",
  "preco": 99.99,
  "ativo": true
}
```

#### Atualizar Produto

```http
PUT /produtos/{id}
Content-Type: application/json

{
  "nome": "Produto Atualizado",
  "descricao": "Descrição atualizada do produto",
  "preco": 149.99,
  "ativo": true
}
```

**Response: 200 OK**

```json
{
  "id": 1,
  "nome": "Produto Atualizado",
  "descricao": "Descrição atualizada do produto",
  "preco": 149.99,
  "ativo": true
}
```

#### Deletar Produto

```http
DELETE /produtos/{id}
```

**Response: 204 No Content**

### Estoques

#### Criar Estoque

```http
POST /estoques
Content-Type: initial/json

{
  "quantidade": 100,
  "estoqueMinimo": 10,
  "produtoId": 1
}
```

**Response: 201 Created**

```json
{
  "id": 1,
  "quantidade": 100,
  "estoqueMinimo": 10
}
```

#### Listar Estoques

```http
GET /estoques?page=0&size=10
```

**Response: 200 OK**

```json
{
  "content": [
    {
      "id": 1,
      "quantidade": 100,
      "estoqueMinimo": 10
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

#### Listar Estoques com Filtros

```http
GET /estoques?quantidade=100&estoqueMinimo=10&produtoId=1&page=0&size=10
```

**Response: 200 OK**

```json
{
  "content": [
    {
      "id": 1,
      "quantidade": 100,
      "estoqueMinimo": 10
    }
  ]
}
```

#### Buscar Estoque por ID

```http
GET /estoques/{id}
```

**Response: 200 OK**

```json
{
  "id": 1,
  "quantidade": 100,
  "estoqueMinimo": 10
}
```

#### Atualizar Estoque

```http
PUT /estoques/{id}
Content-Type: application/json

{
  "quantidade": 150,
  "estoqueMinimo": 15,
  "produtoId": 1
}
```

**Response: 200 OK**

```json
{
  "id": 1,
  "quantidade": 150,
  "estoqueMinimo": 15
}
```

#### Registrar Entrada

```http
POST /estoques/{produtoId}/entrada
Content-Type: application/json

{
  "quantidade": 50
}
```

**Response: 200 OK**

```json
{
  "id": 1,
  "quantidade": 150,
  "estoqueMinimo": 10
}
```

#### Registrar Saída

```http
POST /estoques/{produtoId}/saida
Content-Type: application/json

{
  "quantidade": 20
}
```

**Response: 200 OK**

```json
{
  "id": 1,
  "quantidade": 130,
  "estoqueMinimo": 10
}
```

#### Deletar Estoque

```http
DELETE /estoques/{id}
```

**Response: 204 No Content**

## 🧪 Testes

Para executar os testes unitários:

```bash
./mvnw test
```

Ou usando Maven:

```bash
mvn test
```

## 📄 Estrutura do Projeto

```
API-de-Estoque/
├── src/
│   ├── main/
│   │   ├── java/com/API_de_Estoque/
│   │   │   ├── controller/     # Controladores REST
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── entity/         # Entidades JPA
│   │   │   ├── exception/      # Tratamento de exceções
│   │   │   ├── repository/     # Repositórios JPA
│   │   │   ├── service/        # Lógica de negócio
│   │   │   └── specification/  # Specifications para consultas dinâmicas
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/API_de_Estoque/
│           └── service/        # Testes unitários
├── postman_collection.json
├── pom.xml
└── README.md
```

## 🔍 Validações

### Produto

- **nome**: Obrigatório, máximo 100 caracteres, deve ser único
- **descricao**: Obrigatório, máximo 255 caracteres
- **preco**: Obrigatório, deve ser positivo
- **ativo**: Obrigatório

### Estoque

- **quantidade**: Obrigatório, não pode ser negativa
- **estoqueMinimo**: Obrigatório, não pode ser negativo
- **produtoId**: Obrigatório, deve referenciar um produto existente

### Movimentação

- **quantidade**: Obrigatório, deve ser maior que zero
- **saída**: Não pode exceder a quantidade disponível em estoque
- **entrada/saída**: Produto deve estar ativo

## ⚠️ Tratamento de Erros

A API retorna códigos de status HTTP apropriados:

- **201 Created**: Recurso criado com sucesso
- **200 OK**: Operação realizada com sucesso
- **204 No Content**: Recurso deletado com sucesso
- **400 Bad Request**: Dados inválidos
- **404 Not Found**: Recurso não encontrado
- **409 Conflict**: Nome já cadastrado ou estoque já existe para o produto
- **500 Internal Server Error**: Erro interno no servidor

## 📝 Coleção Postman

Uma coleção Postman está disponível no arquivo `postman_collection.json` para facilitar os testes dos endpoints.

Para importar:
1. Abra o Postman
2. Clique em "Import"
3. Selecione o arquivo `postman_collection.json`
4. A coleção será carregada com todos os endpoints organizados

## 🤝 Contribuindo

Contribuições são bem-vindas! Por favor:
1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

## 👤 Autor

Flavio Alexandre Ribeiro

Desenvolvido como parte do projeto de API de Estoque.

## 📧 Contato

- GitHub: https://github.com/FlavioAlexandreRibeiro
- LinkedIn: https://www.linkedin.com/in/flavioalexandreribeiro1986/?isSelfProfile=true

Para dúvidas ou sugestões, abra uma issue no repositório.
