<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="es">
<head>
  <jsp:include page="../INC/metas.jsp">
    <jsp:param name="titulo" value="Abatech - Error del servidor" />
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
          <h1 class="display-4 text-danger mb-4">¡Ups! Algo salió mal.</h1>
          <p class="lead mb-4">
            Lamentablemente, hemos experimentado un error técnico en nuestro sitio. Estamos trabajando para solucionarlo lo antes posible y restaurar el servicio.
          </p>
          <p class="mb-4">
            Te pedimos disculpas por los inconvenientes que esto pueda causar y agradecemos tu paciencia. Mientras resolvemos el problema, si necesitas asistencia urgente, no dudes en ponerte en contacto con nosotros.
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
