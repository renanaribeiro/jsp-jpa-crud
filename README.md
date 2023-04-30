# JSP JPA Crud

CRUD de Projetos e Pessoas usando JSP e JPA com diversas regras de negócio.

A aplicação irá gerar o DDL automaticamente (criação de tabelas e vínculos), basta indicar a base de dados do PostgreSQL


### Parâmetros de Inicialização

Apontamentos para o banco de dados estão fixos dentro do **application.properties**, não esqueça de alterá-los antes de gerar o build.

## Build

Para compilar o projeto é recomendado executar o seguinte comando com o Maven:

```
mvn clean package install
```