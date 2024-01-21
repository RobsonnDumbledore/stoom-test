# Stoom Store API

Bem-vindo ao repositório da Stoom Store API! Esta é uma aplicação Spring Boot para gerenciamento de produtos

## Acessando a API

### Localmente

Após clonar o repositório e realizar a construção local do projeto, você pode acessar a interface do Swagger para explorar e interagir com a API navegando até: http://localhost:8080/swagger-ui/index.html

Isso abrirá a documentação interativa da API, permitindo que você veja todas as rotas disponíveis, modelos de dados e até mesmo teste as operações diretamente do seu navegador.

### Docker

Se você preferir usar Docker, pode baixar a imagem pronta da aplicação usando o seguinte comando:

```sh
docker pull robsonndumbledore/stoomstore
docker run -p 8080:8080 robsonndumbledore/stoomstore:latest
```
Agora, a API estará disponível em seu ambiente local no mesmo endereço fornecido acima.
### Acesso Online
Para uma visualização rápida e sem a necessidade de setup, a API está hospedada e pode ser acessada no seguinte endereço:
https://stoomstore-74f692538881.herokuapp.com/swagger-ui/index.html#/
Este é o ambiente de produção da aplicação, onde você pode ver a API em ação no mundo real!