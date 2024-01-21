# Stoom Store API

Bem-vindo ao repositório da Stoom Store API! Esta é uma aplicação Spring Boot para gerenciamento de produtos

## Acessando a API

### Localmente

Após clonar o repositório e realizar a construção local do projeto, você pode acessar a interface do Swagger para explorar e interagir com a API navegando até: http://localhost:8080/swagger-ui/index.html

Isso abrirá a documentação interativa da API, permitindo que você veja todas as rotas disponíveis, modelos de dados e até mesmo teste as operações diretamente do seu navegador.

### Docker

Essta aplicação esta disponivel no dockerhub, para executa-la será preciso ter instalado em sua maquina o docker e o docker-compose, para iniciar a aplicação basta na raiz do diretorio onde esta o arquivo 
docker-compose.yml executar o comando abaixo no terminal:
```sh

docker-compose up -d

```
abaixo o arquivo com as configurações dessa aplicação

```yml
version: "3"

services:
  postgres:
    image: postgres
    container_name: stoomstore-database
    environment:
      POSTGRES_DB: store
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: 12345
    restart: always
    volumes:
      - ./postgresql-data:/var/lib/postgresql/data
    ports:
      - "5432:5432"

  app:
    image: robsonndumbledore/stoomstore:latest
    container_name: stoomstore-app
    restart: unless-stopped
    ports:
      - "8080:8080"
    depends_on:
      - postgres

```
Agora, a API estará disponível em seu ambiente local no mesmo endereço fornecido acima.
### Acesso Online
Para uma visualização rápida e sem a necessidade de setup, a API está hospedada e pode ser acessada no seguinte endereço:
https://stoomstore-74f692538881.herokuapp.com/swagger-ui/index.html#/
Este é o ambiente de produção da aplicação, onde você pode ver a API em ação no mundo real!

### Banco De Dados

O Banco de dados dessa aplicação é um banco gratuito e limitado, não sendo ideal para testes de carga, apenas demonstrações simples, ele estará disponivel durante 30 dias.

### Postman
A collection do postman está na raiz do projeto, bastando importar para acessar os endpoints
