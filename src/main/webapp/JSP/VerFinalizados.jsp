<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="es">
<head>
    <jsp:include page="../INC/metas.jsp">
        <jsp:param name="titulo" value="Abatech - Ver pedidos finalizados" />
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

<main class="container">
    <c:choose>
        <c:when test="${empty requestScope.pedidos}">
            <div class="alert alert-warning" role="alert">
                No se ha realizado ningún pedido.
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach var="pedido" items="${requestScope.pedidos}">
                <div class="card mb-3">
                    <div class="card-header" id="heading${pedido.idPedido}">
                        <h2 class="mb-0">
                            <button class="btn" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${pedido.idPedido}" aria-expanded="true" aria-controls="collapse${pedido.idPedido}">
                                Pedido ID: #${pedido.idPedido} - Fecha: <fmt:formatDate value="${pedido.fecha}" type="date" dateStyle="long"/>
                            </button>
                        </h2>
                    </div>
                    <div id="collapse${pedido.idPedido}" class="collapse" aria-labelledby="heading${pedido.idPedido}" data-bs-parent="#accordionExample">
                        <div class="card-body">
                            <table class="table table-striped table-hover table-bordered align-middle">
                                <thead class="table-dark">
                                <tr>
                                    <th scope="col">Imagen</th>
                                    <th scope="col">Producto</th>
                                    <th scope="col" class="text-center">Cantidad</th>
                                    <th scope="col" class="text-end">Precio</th>
                                    <th scope="col" class="text-end">Subtotal</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="linea" items="${pedido.lineasPedido}">
                                    <tr>
                                        <td class="text-center">
                                            <img src="${applicationScope.contexto}/IMG/productos/${linea.producto.imagen}.jpg" alt="${linea.producto.nombre}" class="img-thumbnail" style="width: 50px; height: 50px;">
                                        </td>
                                        <td>${linea.producto.nombre}</td>
                                        <td class="text-center">${linea.cantidad}</td>
                                        <td class="text-end"><fmt:formatNumber value="${linea.producto.precio}" type="currency" currencySymbol="€"/></td>
                                        <td class="text-end"><fmt:formatNumber value="${linea.cantidad * linea.producto.precio}" type="currency" currencySymbol="€"/></td>
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td colspan="4" class="text-end"><strong>Total sin IVA:</strong></td>
                                    <td class="text-end"><fmt:formatNumber value="${pedido.importe / 1.21}" type="currency" currencySymbol="€"/></td>
                                </tr>
                                <tr>
                                    <td colspan="4" class="text-end"><strong>IVA (21%):</strong></td>
                                    <td class="text-end"><fmt:formatNumber value="${pedido.importe - (pedido.importe / 1.21)}" type="currency" currencySymbol="€"/></td>
                                </tr>
                                <tr>
                                    <td colspan="4" class="text-end"><strong>Total:</strong></td>
                                    <td class="text-end"><fmt:formatNumber value="${pedido.importe}" type="currency" currencySymbol="€"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</main>
<c:import url="../INC/footer.jsp"/>
<script src="${bootstrapJS}"></script>
<script src="${javaScript}"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>
</html>