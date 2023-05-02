# JSP JPA Crud

CRUD de Projetos e Pessoas usando JSP e JPA com diversas regras de negócio.

A aplicação irá gerar o DDL automaticamente (criação de tabelas e vínculos), basta indicar a base de dados do PostgreSQL


## 🚀 Parâmetros de Inicialização

Apontamentos para o banco de dados estão fixos dentro do **application.properties**, não esqueça de alterá-los antes de executar.

**Obs:** Talvez seja necessário compilar o projeto antes de executá-lo.

## 🛠 Build

Para compilar o projeto é recomendado executar o seguinte comando com o Maven:

```bash
mvn clean package install
```

## 🔗 Links
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/renanaribeiro)
[![whatsapp](https://img.shields.io/badge/whatsapp-075E54?style=for-the-badge&logo=whatsapp&logoColor=white)](https://wa.me/5511954285091)

## Documentação da API

#### Tabela Pessoa

| Campo       		| Tipo		| Descrição							|
| :----------------	| :-------- | :--------------------------------	|
| `id`				| `Long`  	| ID da Pessoa						|
| `nome`			| `String`	| Nome								|
| `dataNascimento`| `Date`	| Data de Nascimento (yyyy-mm-dd)	|
| `cpf`				| `String`	| CPF								|
| `funcionario`	| `Boolean`| Indicação se é Funcionário		|

#### Tabela Projeto

| Campo       		| Tipo		| Descrição							|
| :----------------	| :-------- | :--------------------------------	|
| `id`				| `Long`  	| ID do Projeto						|
| `nome`			| `String`	| Nome								|
| `dataInicio`| `Date`	| Data de Início (yyyy-mm-dd)	|
| `dataPrevisaoFim`| `Date`	| Data de Previsão (yyyy-mm-dd)	|
| `dataFim`| `Date`	| Data Final (yyyy-mm-dd)	|
| `descricao`				| `String`	| Descrição								|
| `status`	| `StatusEnum`| Status (**obs:** aleatório e não persiste no banco)		|
| `orcamento` | `Double` | Orçamento com valor fracionado por ponto ( . ) |
| `risco`   | `RiscoEnum` | Risco (**obs:** indicado apenas na web e não persiste no banco) |
| `gerente` | `Pessoa`  | Vinculado com a tabela Pessoa com **funcionario = true** |

**CRUD Pessoa**
#### Listar Pessoas

```
  GET /pessoas/listar
```
```json
[
    {
        "id": 1,
        "nome": "Renan de Araujo Ribeiro",
        "dataNascimento": "1994-02-21",
        "cpf": "123.456.789-10",
        "funcionario": false
    },
    {
        "id": 2,
        "nome": "Funcionário Destaque do Mês",
        "dataNascimento": "2023-05-02",
        "cpf": "987.654.321-00",
        "funcionario": true
    }
]
```

#### Listar Funcionários

```
  GET /pessoas/listar-funcionarios
```
```json
[
    {
        "id": 2,
        "nome": "Funcionário Destaque do Mês",
        "dataNascimento": "2023-05-02",
        "cpf": "987.654.321-00",
        "funcionario": true
    }
]
```

#### Procurar Pessoa por ID

```
  GET /pessoas/{id}
```
```json
{
    "id": 1,
    "nome": "Renan de Araujo Ribeiro",
    "dataNascimento": "1994-02-21",
    "cpf": "123.456.789-10",
    "funcionario": false
}
```

#### Criar Pessoa

```
  POST /pessoas/criar
```
```json
{
    "nome": "Funcionário Destaque do Mês",
    "dataNascimento": "2023-05-02",
    "cpf": "987.654.321-00",
    "funcionario": true
}
```

#### Editar Pessoa

```
  PUT /pessoas/editar
```
```json
{
    "id": 2,
    "nome": "Funcionário Destaque do Mês",
    "dataNascimento": "2023-05-02",
    "cpf": "987.654.321-10",
    "funcionario": true
}
```

#### Excluir Pessoa

```
  DELETE /pessoas/excluir/{id}
```
```
true (200 OK) / false (404 Not Found)
```


**CRUD Projetos**: Realizar pelo navegador em `http://localhost:8080/projetos`
Obs: Caso a página não carregue os estilos na primeira visualização, basta racarregá-la


#### Lista de Projetos
![Listar Projetos](https://user-images.githubusercontent.com/15999688/235762253-779531e4-307b-445d-8c70-19abf3aa970c.png)

#### Adicionar Projeto
![Adicionar Projeto](https://user-images.githubusercontent.com/15999688/235762383-d454379c-9e08-4b3c-bb8a-1e4a3328a196.png)

#### Editar Projeto
![Editar Projeto](https://user-images.githubusercontent.com/15999688/235762343-40b13580-a79f-4255-8666-0f81f9f85bec.png)
