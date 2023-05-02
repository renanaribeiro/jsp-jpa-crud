<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt_br">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="refresh" content="5" >
        
        <title>Projetos</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
        <link href="<c:url value="/static/node_modules/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
        <script src="<c:url value="/static/node_modules/bootstrap/dist/js/bootstrap.min.js"/>"></script>
    </head>

    <body>
        <header>
            <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #017bc4">
                <div class="container-fluid">
                    <div>
                        <a href="#" class="navbar-brand"> Gerenciamento de Projetos </a>
                    </div>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    
                    <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
                        <ul class="navbar-nav">
                            <li><a href="/projetos" class="nav-link">Projetos</a></li>
                        </ul>
                    </div>
                </div>
            </nav>

        </header>
        <br>
        
        <div class="container">
            <h3 class="text-center">Lista de Projetos</h3>
            <hr>
            <table class="table table-striped table-hover table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Data de Início</th>
                        <th>Data de Previsão</th>
                        <th>Data Final</th>
                        <th>Descrição</th>
                        <th>Status</th>
                        <th>Orçamento</th>
                        <th>Risco</th>
                        <th>Gerente</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="proj" items="${projetos}">
                        <tr>
                            <td><c:out value="${proj.id}" /></td>
                            <td><c:out value="${proj.nome}" /></td>
                            <td><c:out value="${proj.dataInicio}" /></td>
                            <td><c:out value="${proj.dataPrevisaoFim}" /></td>
                            <td><c:out value="${proj.dataFim}" /></td>
                            <td><c:out value="${proj.descricao}" /></td>
                            <td><c:out value="${proj.status}" /></td>
                            <td><c:out value="${proj.orcamento}" /></td>
                            <td><c:out value="${proj.risco}" /></td>
                            <td><c:out value="${proj.gerente.nome}" /></td>
                            <td>
                                <a href="projetos/atualizar/${proj.id}" class="btn btn-primary">Editar</a>
                                <a href="projetos/excluir/${proj.id}" class="btn btn-danger" onclick="return confirm('Tem certeza que deseja excluir este projeto?');">Excluir</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <center>
                <a href="projetos/novo" class="btn btn-success custom-add-btn">Adicionar Projeto</a>
            </center>
        </div>
    </body>
</html>