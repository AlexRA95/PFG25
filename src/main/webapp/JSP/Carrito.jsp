<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="es">
<head>
    <jsp:include page="../INC/metas.jsp">
        <jsp:param name="titulo" value="Abatech - Carrito" />
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
    <div class="row">
        <div class="row">
            <c:choose>
                <c:when test="${empty sessionScope.carrito.lineasPedido}">
                    <div class="alert alert-warning" role="alert">
                        No se ha añadido ningún producto al pedido.
                    </div>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped table-hover table-bordered align-middle">
                        <thead class="table-dark">
                        <tr>
                            <th scope="col">Imagen</th>
                            <th scope="col">Producto</th>
                            <th scope="col" class="text-center">Cantidad</th>
                            <th scope="col" class="text-end">Precio</th>
                            <th scope="col" class="text-end">Subtotal</th>
                            <th scope="col" class="text-center">Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="linea" items="${sessionScope.carrito.lineasPedido}">
                            <tr>
                                <td class="text-center">
                                    <img src="${applicationScope.contexto}/IMG/productos/${linea.producto.imagen}.jpg" alt="${linea.producto.nombre}" class="img-thumbnail" style="width: 50px; height: 50px;">
                                </td>
                                <td>${linea.producto.nombre}</td>
                                <td class="text-center">${linea.cantidad}</td>
                                <td class="text-end"><fmt:formatNumber value="${linea.producto.precio}" type="currency" currencySymbol="€"/></td>
                                <td class="text-end"><fmt:formatNumber value="${linea.cantidad * linea.producto.precio}" type="currency" currencySymbol="€"/></td>
                                <td class="text-center">
                                    <form action="${applicationScope.contexto}/GestionCarrito" method="post" style="display:inline;">
                                        <input type="hidden" name="idProducto" value="${linea.producto.idProducto}">
                                        <button type="submit" name="opcion" value="incrementar" class="btn btn-success btn-sm" title="Añadir">
                                            <i class="bi bi-plus"></i>
                                        </button>
                                        <button type="submit" name="opcion" value="decrementar" class="btn btn-danger btn-sm" title="Quitar">
                                            <i class="bi bi-dash"></i>
                                        </button>
                                        <button type="submit" name="opcion" value="eliminar" class="btn btn-outline-danger btn-sm" title="Quitar">
                                            <i class="bi bi-trash3"></i>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="5" class="text-end"><strong>Base imponible:</strong></td>
                            <td class="text-end"><fmt:formatNumber value="${sessionScope.carrito.importe / 1.21}" type="currency" currencySymbol="€"/></td>
                        </tr>
                        <tr>
                            <td colspan="5" class="text-end"><strong>IVA (21%):</strong></td>
                            <td class="text-end"><fmt:formatNumber value="${sessionScope.carrito.importe - (sessionScope.carrito.importe / 1.21)}" type="currency" currencySymbol="€"/></td>
                        </tr>
                        <tr>
                            <td colspan="5" class="text-end"><strong>Total a pagar:</strong></td>
                            <td class="text-end"><fmt:formatNumber value="${sessionScope.carrito.importe}" type="currency" currencySymbol="€"/></td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="row">
                        <form action="${applicationScope.contexto}/GestionCarrito" method="post">
                            <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#confirmModal">Vaciar carrito</button>
                            <button type="submit" name="opcion" value="comprar" class="btn btn-success" <c:if test="${empty sessionScope.usuario}">disabled</c:if>>Comprar</button>
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</main>

<!-- Modal para confirmar cerrar sesión -->
<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmModalLabel">Confirmar acción</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                ¿Estás seguro de que deseas vaciar el carrito?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <form action="${applicationScope.contexto}/GestionCarrito" method="post" style="display:inline;">
                    <button type="submit" name="opcion" value="vaciar" class="btn btn-danger">Vaciar carrito</button>
                </form>
            </div>
        </div>
    </div>
</div>
<c:import url="../INC/footer.jsp"/>
<script src="${bootstrapJS}"></script>
<script src="${javaScript}"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>
</html>