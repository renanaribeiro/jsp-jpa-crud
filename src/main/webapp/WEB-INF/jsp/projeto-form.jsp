<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@page import="br.com.renanribeiro.model.Pessoa" %>
<%@page import="java.util.ArrayList" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt_br">
    <%
        ArrayList<Pessoa> list = new ArrayList<Pessoa>();
        list = (ArrayList<Pessoa>) request.getAttribute("funcionarios");
    %>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <title>Salvar Projeto</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
        <link href="<c:url value="/static/node_modules/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
        <script src="<c:url value="/static/node_modules/bootstrap/dist/js/bootstrap.min.js"/>"></script>

        <script>
        // Example starter JavaScript for disabling form submissions if there are invalid fields
            (function() {
                'use strict';
                window.addEventListener('load', function() {
                    // Fetch all the forms we want to apply custom Bootstrap validation styles to
                    var forms = document.getElementsByClassName('needs-validation');
                    // Loop over them and prevent submission
                    var validation = Array.prototype.filter.call(forms, function(form) {
                        form.addEventListener('submit', function(event) {
                            if (form.checkValidity() === false) {
                                event.preventDefault();
                                event.stopPropagation();
                            }
                            form.classList.add('was-validated');
                        }, false);
                    });
                }, false);
            })();
        </script>
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

        <div class="row">
            <div class="container col-md-5">
                <div class="card">
                    <div class="card-body">
                        <c:if test="${projeto != null}">
                            <form action="/projetos/editar" method="post" class="form-horizontal needs-validation" novalidate>
                        </c:if>
                        <c:if test="${projeto == null}">
                            <form action="criar" method="post" class="form-horizontal needs-validation" novalidate>
                        </c:if>
    
                            <caption>
                                <h2 class="text-center add-space-under">
                                    <c:if test="${projeto != null}">
                                        Editar Projeto
                                    </c:if>
                                    <c:if test="${projeto == null}">
                                        Adicionar Projeto
                                    </c:if>
                                </h2>
                            </caption>
    
                            <c:if test="${projeto != null}">
                                <input type="hidden" name="id" value="<c:out value='${projeto.id}' />" />
                            </c:if>

                            <div class="form-group row add-space-under">
                                <label for="nome" class="col-sm-3 col-form-label">Nome</label>
                                <div class="col-sm-9">
                                    <input type="text" value="<c:out value='${projeto.nome}' />" class="form-control" id="nome" name="nome" placeholder="Nome do Projeto" required>
                                    <div class="invalid-feedback">
                                        Nome do projeto é obrigatório!
                                    </div>
                                </div>
                            </div>

                            <div class="form-group row add-space-under">
                                <label for="dataInicio" class="col-sm-3 col-form-label">Data de Início</label>
                                <div class="col-sm-9">
                                    <input type="date" value="<c:out value='${projeto.dataInicio}' />" class="form-control" id="dataInicio" name="dataInicio" required>
                                    <div class="invalid-feedback">
                                        Data de início é obrigatório!
                                    </div>
                                </div>
                            </div>

                            <div class="form-group row add-space-under">
                                <label for="dataPrevisaoFim" class="col-sm-3 col-form-label">Data de Previsão</label>
                                <div class="col-sm-9">
                                    <input type="date" value="<c:out value='${projeto.dataPrevisaoFim}' />" class="form-control" id="dataPrevisaoFim" name="dataPrevisaoFim" required>
                                    <div class="invalid-feedback">
                                        Data de previsão é obrigatório!
                                    </div>
                                </div>
                            </div>

                            <div class="form-group row add-space-under">
                                <label for="dataFim" class="col-sm-3 col-form-label">Data Final</label>
                                <div class="col-sm-9">
                                    <input type="date" value="<c:out value='${projeto.dataFim}' />" class="form-control" id="dataFim" name="dataFim" required>
                                    <div class="invalid-feedback">
                                        Data final é obrigatório!
                                    </div>
                                </div>
                            </div>
    
                            <div class="form-group row add-space-under">
                                <label for="descricao" class="col-sm-3 col-form-label">Descrição</label>
                                <div class="col-sm-9">
                                    <input type="text" value="<c:out value='${projeto.descricao}' />" class="form-control" id="descricao" name="descricao" placeholder="Mais detalhes sobre o projeto">
                                </div>
                            </div>

                            <div class="form-group row add-space-under">
                                <label for="orcamento" class="col-sm-3 col-form-label">Orçamento</label>
                                <div class="col-sm-9">
                                    <div class="input-group">
                                        <div class="input-group-prepend ">
                                            <span class="input-group-text">R$</span>
                                        </div>
                                        <input type="text" value="<c:out value='${projeto.orcamento}' />" class="form-control" id="orcamento" name="orcamento" aria-label="Reais" required>
                                        <div class="invalid-feedback">
                                            Orçamento é obrigatório!
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="risco" class="col-sm-3 col-form-label">Risco:</label>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="risco" id="baixo" value="baixo" required>
                                    <label class="form-check-label" for="baixo">Baixo</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="risco" id="medio" value="medio" required>
                                    <label class="form-check-label" for="medio">Médio</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="risco" id="alto" value="alto" required>
                                    <label class="form-check-label" for="alto">Alto</label>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="gerente" class="col-sm-3 col-form-label">Gerente:</label>
                                <c:forEach var="func" items="${funcionarios}">
                                    <div class="form-check col-sm-9 offset-sm-3">
                                        <input class="form-check-input" type="radio" name="gerenteId" id="gerenteId" value="${func.id}" required>
                                        <label class="form-check-label" for="gerenteId">
                                            <c:out value="${func.nome}" />
                                        </label>
                                    </div>
                                </c:forEach>
                            </div>

                            <br>
                            <button type="submit" class="btn btn-success">Salvar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>

</html>
