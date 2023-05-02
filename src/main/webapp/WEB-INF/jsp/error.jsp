<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt_br">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <title>Erro</title>
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
            <h3 class="text-center">Erro</h3>
            <hr>
            <br>
            <center>
                <h2><c:out value="${message}" /><br/></h2>
            </center>
        </div>
    </body>
</html>
