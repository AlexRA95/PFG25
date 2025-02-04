<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="es">
<head>
    <jsp:include page="../INC/metas.jsp">
        <jsp:param name="titulo" value="Abatech - Página no encontrada" />
    </jsp:include>
    <link rel="stylesheet" href="${bootstrap}"/>
</head>
<body>

<c:choose>
    <c:when test="${empty sessionScope.usuario}">
        <c:import url="../INC/headerAnon.jsp"/>
    </c:when>
    <c:when test="${not empty sessionScope.usuario}">
        <c:import url="../INC/headerLogged.jsp"/>
    </c:when>
</c:choose>

<main class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6 text-center">
            <div class="card shadow-lg">
                <div class="card-body">
                    <h1 class="display-4 text-danger mb-4">¡Vaya! La página no se encuentra.</h1>
                    <p class="lead mb-4">
                        Lamentablemente, no hemos podido encontrar la página que estás buscando. Es posible que se haya movido, eliminado o que la dirección esté incorrecta.
                    </p>
                    <p class="mb-4">
                        Te pedimos disculpas por cualquier inconveniente.
                    </p>
                    <p class="mb-4">
                        Si necesitas asistencia, no dudes en ponerte en contacto con nosotros.
                    </p>
                    <p class="mb-4">
                        Gracias por tu comprensión.
                    </p>
                    <form action="${applicationScope.contexto}/Frontcontroller" method="post">
                        <button type="submit" name="opcion" value="nada" class="btn btn-primary btn-lg">Volver al inicio</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<c:import url="../INC/footer.jsp"/>
<script src="${bootstrapJS}"></script>
<script src="${javaScript}"></script>
</body>
</html>
