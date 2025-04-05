# Acervo Fácil 📚

**Acervo Fácil** é uma aplicação web desenvolvida com **Java + Spring Boot** que fornece uma API RESTful para o gerenciamento de usuários do sistema, como **clientes, funcionários, contatos e endereços**. O foco está na **padronização de respostas**, uso de **DTOs**, separação de camadas (Controller, Service, Mapper, Repository) e documentação via Swagger/OpenAPI.

---

## 🚀 Visão Geral do Projeto

O projeto tem como objetivo oferecer uma base sólida para sistemas administrativos que envolvam cadastros, autenticação e relacionamento entre entidades. A aplicação está estruturada com boas práticas de desenvolvimento como:

- Separação de responsabilidades (Controller, Service, Repository, Mapper)
- Uso de **MapStruct** para conversão entre entidades e DTOs
- Documentação automatizada com **SpringDoc OpenAPI**
- Manipulação de exceções centralizada
- Respostas padronizadas para os endpoints
- Integração com banco de dados **PostgreSQL**
- Suporte a autenticação JWT

---

## 🧱 Tecnologias Utilizadas

| Camada               | Tecnologia                        |
|----------------------|-----------------------------------|
| Linguagem            | Java 17                           |
| Framework Principal  | Spring Boot 3.4.3                 |
| ORM                  | Spring Data JPA                   |
| Banco de Dados       | PostgreSQL                        |
| Mapeamento DTO       | MapStruct 1.6.3                   |
| Documentação         | Springdoc OpenAPI (Swagger UI)   |
| Validações           | Jakarta Validation                |
| Segurança (futura)   | JWT (JJWT 0.11.5)                 |
| Testes               | Spring Boot Starter Test          |

---

## 📁 Estrutura de Pastas

acervofacil/
│
├── api/                     # Camada de interface com o mundo externo
│   ├── controller/          # Controllers REST
│   ├── dto/                 # DTOs de entrada e saída
│   ├── response/            # Estruturas padronizadas de resposta
│   └── utils/               # Utilitários da camada de API (ex: ApiUtils)
│
├── domain/                  # Lógica de domínio
│   ├── model/               # Entidades JPA
│   ├── service/             # Interfaces e implementações da regra de negócio
│   └── repository/          # Interfaces JPA (Spring Data)
│
├── mapper/                  # Mappers com MapStruct para conversão entre entidades e DTOs
│
├── exception/               # Tratamento global de exceções (Handler, mensagens, etc.)
│
└── AcervoFacilApplication.java  # Classe principal da aplicação (Spring Boot Starter)


---

## 📚 Endpoints REST

A documentação interativa está disponível após subir o projeto em:

http://localhost:8080/swagger-ui.html

Ou diretamente em: http://localhost:8080/swagger-ui/index.html


### Exemplos:

- `GET /api/v1/clientes/{id}` → Busca cliente por ID  
- `POST /api/v1/clientes` → Cadastra um novo cliente  
- `GET /api/v1/contatos/{id}` → Retorna contato vinculado a qualquer usuário do sistema  
- `GET /api/v1/clientes/buscar?cpf=XXX` → Busca cliente por CPF  
- `GET /api/v1/clientes?page=0&size=10` → Lista paginada de clientes

---

## ⚙️ Como Rodar o Projeto

### Pré-requisitos:

- Java 17
- Maven 3.8+
- PostgreSQL

### Passos:

1. **Clone o repositório:**


git clone https://github.com/gssantos17/acervofacil.git
cd acervofacil

spring.datasource.url=jdbc:postgresql://localhost:5432/acervofacil
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

./mvnw spring-boot:run

