
# Price Alert API - EN/US

A robust backend service built with Java and Spring Boot that monitors product prices from online stores. It sends email alerts to users when a product's price drops below their desired target.

## ✨ Features

- **Web Scraping**: Dynamically scrapes product information like name and price from store URLs.
- **Scheduled Monitoring**: Automatically checks for price updates at regular intervals.
- **Email Notifications**: Sends an alert to the user's email when the price drops below the target.
- **RESTful API**: Provides a simple interface to create, manage, and view alerts.
- **Database Integration**: Persists users, products, alerts, and price history.
- **Containerized**: Includes a `Dockerfile` and `docker-compose.yml` for easy setup and deployment.

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3.5.0**
- **Spring Data JPA**: For database interaction.
- **Maven**: For dependency management and building.
- **Docker**: For containerization.
- **PostgreSQL / H2**: (Or any configured JPA-compatible database).
- **Thymeleaf**: For rendering HTML templates.
- **Java Mail**: For sending email notifications.

## 🚀 Getting Started

Follow these instructions to get the project running on your local machine for development and testing.

### Prerequisites

- [Java Development Kit (JDK) 21](https://www.oracle.com/java/technologies/downloads/#java21)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [Docker and Docker Compose](https://www.docker.com/products/docker-desktop/)

### 1. Running with Docker (Recommended)

This is the easiest way to get the application and its database up and running.

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/pedrozaz/price-alert.git
    cd price-alert
    ```

2.  **Configure Email Settings:**
    The application needs SMTP credentials to send emails. You can configure this by setting environment variables in the `docker-compose.yml` file. Find the `environment` section for the `pricealert-app` service and fill in your details:
    ```yaml
    environment:
      - SPRING_MAIL_HOST=your-smtp-host
      - SPRING_MAIL_PORT=587
      - SPRING_MAIL_USERNAME=your-email@example.com
      - SPRING_MAIL_PASSWORD=your-email-password
    ```

3.  **Build and run the application:**
    ```sh
    docker-compose up --build
    ```

The API will be running and accessible at `http://localhost:8080`.

### 2. Running Locally with Maven

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/pedrozaz/price-alert.git
    cd price-alert
    ```

2.  **Configure the application:**
    Open the `src/main/resources/application.yml` file and configure your database connection and email server settings.

3.  **Run the application:**
    ```sh
    ./mvnw spring-boot:run
    ```

The API will be running and accessible at `http://localhost:8080`.

## 🧪 How to Test the API

You can use a tool like [Postman](https://www.postman.com/), [Insomnia](https://insomnia.rest/), or a `curl` command to interact with the API.

### Create a New Price Alert

Send a `POST` request to the `/api/alerts` endpoint to create a new alert. The system will scrape the initial price and save the alert.

- **Endpoint:** `POST /api/alerts`
- **Body:**
  ```json
  {
    "productUrl": "https://www.amazon.com.br/dp/your-product-id",
    "targetPrice": 4500.00,
    "userEmail": "your-email@example.com"
  }
  ```

#### Example using `curl`:

```sh
curl -X POST \
  http://localhost:8080/api/alerts \
  -H 'Content-Type: application/json' \
  -d '{
    "productUrl": "https://www.amazon.com.br/dp/B08J66G336",
    "targetPrice": 500.0,
    "userEmail": "your-email@example.com"
  }'
```

If the request is successful, the application will start monitoring the product. When the price drops below the `targetPrice`, an email will be sent to `userEmail`.


---

# API Alerta de Preços - PT/BR

Um serviço de backend robusto construído com Java e Spring Boot que monitora os preços de produtos em lojas online. Ele envia alertas por e-mail aos usuários quando o preço de um produto cai abaixo do valor desejado.

## ✨ Funcionalidades

- **Web Scraping**: Extrai dinamicamente informações de produtos, como nome e preço, a partir de URLs de lojas.
- **Monitoramento Agendado**: Verifica automaticamente as atualizações de preços em intervalos regulares.
- **Notificações por E-mail**: Envia um alerta para o e-mail do usuário quando o preço cai abaixo do valor alvo.
- **API RESTful**: Fornece uma interface simples para criar, gerenciar e visualizar alertas.
- **Integração com Banco de Dados**: Persiste usuários, produtos, alertas e histórico de preços.
- **Containerizado**: Inclui um `Dockerfile` e `docker-compose.yml` para facilitar a configuração e o deploy.

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.0**
- **Spring Data JPA**: Para interação com o banco de dados.
- **Maven**: Para gerenciamento de dependências e build.
- **Docker**: Para containerização.
- **PostgreSQL / H2**: (Ou qualquer banco de dados compatível com JPA configurado).
- **THymeleaf**: Para renderização de templates HTML.
- **Java Mail**: Para envio de notificações por e-mail.

## 🚀 Como Começar

Siga estas instruções para executar o projeto em sua máquina local para desenvolvimento e testes.

### Pré-requisitos

- [Java Development Kit (JDK) 21](https://www.oracle.com/java/technologies/downloads/#java21)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [Docker e Docker Compose](https://www.docker.com/products/docker-desktop/)

### 1. Executando com Docker (Recomendado)

Esta é a maneira mais fácil de colocar a aplicação e seu banco de dados para funcionar.

1.  **Clone o repositório:**
    ```sh
    git clone https://github.com/pedrozaz/price-alert.git
    cd price-alert
    ```

2.  **Configure as Credenciais de E-mail:**
    A aplicação precisa de credenciais SMTP para enviar e-mails. Você pode configurar isso definindo variáveis de ambiente no arquivo `docker-compose.yml`. Encontre a seção `environment` do serviço `pricealert-app` e preencha com seus dados:
    ```yaml
    environment:
      - SPRING_MAIL_HOST=seu-host-smtp
      - SPRING_MAIL_PORT=587
      - SPRING_MAIL_USERNAME=seu-email@example.com
      - SPRING_MAIL_PASSWORD=sua-senha-de-email
    ```

3.  **Construa e execute a aplicação:**
    ```sh
    docker-compose up --build
    ```

A API estará em execução e acessível em `http://localhost:8080`.

### 2. Executando Localmente com Maven

1.  **Clone o repositório:**
    ```sh
    git clone https://github.com/pedrozaz/price-alert.git
    cd price-alert
    ```

2.  **Configure a aplicação:**
    Abra o arquivo `src/main/resources/application.yml` e configure sua conexão com o banco de dados e as configurações do servidor de e-mail.

3.  **Execute a aplicação:**
    ```sh
    ./mvnw spring-boot:run
    ```

A API estará em execução e acessível em `http://localhost:8080`.

## 🧪 Como Testar a API

Você pode usar uma ferramenta como [Postman](https://www.postman.com/), [Insomnia](https://insomnia.rest/) ou o comando `curl` para interagir com a API.

### Criar um Novo Alerta de Preço

Envie uma requisição `POST` para o endpoint `/api/alerts` para criar um novo alerta. O sistema irá extrair o preço inicial e salvar o alerta.

- **Endpoint:** `POST /api/alerts`
- **Corpo da Requisição (Body):**
  ```json
  {
    "productUrl": "https://www.amazon.com.br/dp/seu-id-de-produto",
    "targetPrice": 4500.00,
    "userEmail": "seu-email@example.com"
  }
  ```

#### Exemplo usando `curl`:

```sh
curl -X POST \
  http://localhost:8080/api/alerts \
  -H 'Content-Type: application/json' \
  -d '{
    "productUrl": "https://www.amazon.com.br/dp/B08J66G336",
    "targetPrice": 500.0,
    "userEmail": "seu-email@example.com"
  }'
```

Se a requisição for bem-sucedida, a aplicação começará a monitorar o produto. Quando o preço cair abaixo do `targetPrice`, um e-mail será enviado para o `userEmail`.
