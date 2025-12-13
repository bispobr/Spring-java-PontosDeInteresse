# POI (Pontos de Interesse) API

Esta API  permite o cadastro e a consulta de Pontos de Interesse (POIs), incluindo a busca por proximidade a partir de um ponto de referência definido pelo usuário.

## Funcionalidades

- Cadastro de POIs com nome e coordenadas (X, Y).
- Listagem de todos os POIs cadastrados.
- Consulta de POIs por proximidade com base em uma distância máxima (d-max).
- Remoção de um POI.
- Atualização de um POI.

## Regras de Negócio

### Cadastro de POIs

Cada Ponto de Interesse (POI)  contem os seguintes atributos obrigatórios:

- `nome`: string representando o nome do POI.
- `coordenadaX`: número inteiro não negativo.
- `coordenadaY`: número inteiro não negativo.

As informações são armazenadas de forma persistente em uma base de dados.

---

### Listagem de POIs

- **Listar todos os POIs cadastrados**: retorna todos os registros da base de dados.
- **Listar POIs por proximidade**:
  - Requer uma coordenada de referência (`coordenadaX`, `coordenadaY`) e uma distância máxima (`d-max`, em metros).
  - Retorna todos os POIs cuja distância até o ponto de referência seja **menor ou igual** a `d-max`.

O cálculo da distância entre pontos é baseado na fórmula euclidiana.

## Tecnologias Utilizadas

- **Java + Spring Boot** – Framework principal da aplicação
- **Lombok (@Slf4j)** – Geração de logs
- **Swagger** – Documentação da API
- **Spring Boot Actuator** – Monitoramento e verificação de saúde da aplicação
- **Integração Actuator + Swagger** – Permite monitorar a saúde da API diretamente pela interface de documentação
- **H2 DataBase** – Banco de dados utilizado
- **Tratamento de Exceções** - @RestControllerAdvice
- **JUnit 5 + Mockito** – Testes Unitarios
- **Docker** – criação, implantação e gerenciamento de aplicações dentro de contêineres.

## Requisitos

- Java 21+
- Maven

## Executando o Projeto

1. Clone o repositório:

```bash
git hhttps://github.com/bispobr/Spring-java-PontosDeInteresse.git
```

## Como usar

1. Inicie a aplicação
2. A API está acessível através do endereço http://localhost:8080
3. A documentação da API está acessível através do Link http://localhost:8080/swagger-ui/index.html#/
4. O endpoint de saúde e métricas do Actuator está acessível através do Link http://localhost:8080/actuator/health

## Como Rodar em um Container (Opcional)

1. Construa o projeto

```bash
mvn clean package 
```

2. Gere a Imagem Docker, com o Docker  instalado execute:


```bash
docker build -t gps . 
```

3. Execute o Container

```bash
docker run -p 8080:8080 gps
```


## API Endpoints

API contém os seguinte endpoint :

```http request
POST /pontosInteresse/cadastro - Cadastra um novo POI.
Content-Type: application/json

{
   "nome" : "xxxxxx",
   "x": 00,
   "y" : 00
}
```
| Parâmetro | Tipo     | Descrição                           |
|:----------|:---------| :---------------------------------- |
| `nome`    | `String` | **Obrigatório**. O nome do POI 
| `x`       | `Long`   | **Obrigatório**.  Coordenada x do POI 
| `y`       | `Long`   | **Obrigatório**. Coordenada y do POI 

```http request
PUT /pontosInteresse/{id} - Atualiza um POI existente.
Content-Type: application/json

{
   "nome" : "xxxxxx",
   "x": 00,
   "y" : 00
}
```
| Parâmetro | Tipo     | Descrição                           |
|:----------|:---------| :---------------------------------- |
| `nome`    | `String` | **Obrigatório**. O nome do POI 
| `x`       | `Long`   | **Obrigatório**.  Coordenada x do POI 
| `y`       | `Long`   | **Obrigatório**. Coordenada y do POI 


```http request
DEL /pontosInteresse/{id} - Remove o POI indicado  
```

```http request
GET /pontosInteresse/listagem - retorna todos os  Pontos de interesse cadastrados.

```
```http request
GET /pontosInteresse/proximos?x={x}&y={y}&dmax={distancia} - retorna os pontos de interesse proximo dos pontos indicados  
```
| Parâmetro | Tipo     | Descrição                           |
|:----------|:---------| :---------------------------------- |
| `x`       | `Long`   | **Obrigatório**.  Coordenada x do POI 
| `y`       | `Long`   | **Obrigatório**. Coordenada y do POI 
| `dmax`    | `String` | **Obrigatório**. Distancia maxima 