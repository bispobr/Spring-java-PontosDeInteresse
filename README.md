# POI (Pontos de Interesse) API

API REST desenvolvida com Java e Spring Boot para cadastro, atualização, exclusão e consulta de Pontos de Interesse (POIs), incluindo busca por proximidade a partir de uma coordenada de referência.

## Funcionalidades

- Cadastro de POIs com nome e coordenadas (X, Y)
- Listagem de POIs cadastrados
- Consulta de POIs por proximidade
- Remoção de POIs
- Atualização de POIs
- Documentação da API com Swagger/OpenAPI
- Monitoramento com Spring Boot Actuator

## Regras de Negócio

### Cadastro de POIs

Cada Ponto de Interesse possui os seguintes atributos:

- `nome`: nome do POI
- `coordenadaX`: coordenada X, representada por número inteiro não negativo
- `coordenadaY`: coordenada Y, representada por número inteiro não negativo

### Consulta por proximidade

A consulta requer uma coordenada de referência e uma distância máxima (`dmax`). São retornados os POIs cuja distância até o ponto de referência seja menor ou igual à distância informada.

O cálculo da distância entre os pontos utiliza a fórmula euclidiana.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **H2 Database**
- **Swagger / OpenAPI**
- **Spring Boot Actuator**
- **JUnit 5 + Mockito**
- **Docker**
- **Lombok**

## Requisitos

- Java 21+
- Maven
- Docker (opcional)

## Executando o Projeto

Clone o repositório:

```bash
git clone https://github.com/bispobr/Spring-java-PontosDeInteresse.git
cd Spring-java-PontosDeInteresse
```

Execute a aplicação:

```bash
mvn spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

## Swagger / OpenAPI

A documentação da API está disponível em:

```text
http://localhost:8080/swagger-ui/index.html
```

## H2 Console

O console do H2 está habilitado em:

```text
http://localhost:8080/h2-console
```

## Actuator

Endpoint de saúde:

```text
http://localhost:8080/actuator/health
```

Endpoint de métricas:

```text
http://localhost:8080/actuator/metrics
```

## Docker

Para gerar o artefato da aplicação:

```bash
mvn clean package
```

Para gerar a imagem Docker:

```bash
docker build -t gps .
```

Para executar o container:

```bash
docker run -p 8080:8080 gps
```

## API Endpoints

### Cadastrar POI

```http
POST /pontosInteresse/cadastro
Content-Type: application/json
```

Exemplo:

```json
{
  "nome": "Restaurante",
  "x": 10,
  "y": 20
}
```

### Atualizar POI

```http
PUT /pontosInteresse/{id}
Content-Type: application/json
```

Exemplo:

```json
{
  "nome": "Restaurante Atualizado",
  "x": 15,
  "y": 25
}
```

### Remover POI

```http
DELETE /pontosInteresse/{id}
```

### Listar POIs

```http
GET /pontosInteresse/listagem
```

### Buscar POIs por proximidade

```http
GET /pontosInteresse/proximos?x={x}&y={y}&dmax={distancia}
```

| Parâmetro | Tipo | Descrição |
|---|---|---|
| `x` | `Long` | Coordenada X do ponto de referência |
| `y` | `Long` | Coordenada Y do ponto de referência |
| `dmax` | `Long` | Distância máxima considerada na busca |

## Testes

Execute os testes com:

```bash
mvn test
```

## Status

Projeto de estudo desenvolvido para praticar desenvolvimento de APIs REST com Spring Boot, persistência com JPA/H2, tratamento de exceções e consultas por proximidade.
