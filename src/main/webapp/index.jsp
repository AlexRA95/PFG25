<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:url var="bootstrap" value="/CSS/bootstrap.min.css" scope="application" />
<c:url var="bootstrapJS" value="/JS/bootstrap.bundle.min.js" scope="application" />
<c:url var="estilo" value="/CSS/style.css" scope="application"/>
<c:url var="javaScript" value="/JS/script.js" scope="application"/>
<c:set var="contexto" value="${pageContext.request.contextPath}" scope="application" />
<html lang="es">
<head>
    <jsp:include page="INC/metas.jsp">
        <jsp:param name="titulo" value="Abatech - Inicio" />
    </jsp:include>
    <link rel="stylesheet" href="${bootstrap}"/>
    <link rel="stylesheet" href="${estilo}"/>
</head>
<body>
<c:choose>
    <c:when test="${empty sessionScope.usuario}">
        <c:import url="INC/headerAnon.jsp"/>
    </c:when>
    <c:when test="${not empty sessionScope.usuario}">
        <c:import url="INC/headerLogged.jsp"/>
    </c:when>
</c:choose>

<main class="container py-4">
    <div class="row">
        <!-- Sidebar de filtros -->
        <aside class="col-md-3">
            <div class="card shadow-sm border-light rounded-3">
                <h2 class="card-header text-center">Filtros</h2>
                <form action="${contexto}/FiltrarProds" method="post">
                    <div class="mb-3 d-grid gap-2">
                        <button type="submit" class="btn btn-primary">Filtrar</button>
                    </div>
                    <div class="accordion" id="filterAccordion">
                        <!-- Categorías -->
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingCategory">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseCategory" aria-expanded="false" aria-controls="collapseCategory">
                                    Categor&iacute;as
                                </button>
                            </h2>
                            <div id="collapseCategory" class="accordion-collapse collapse" aria-labelledby="headingCategory" data-bs-parent="#filterAccordion">
                                <div class="accordion-body">
                                    <c:forEach var="categoria" items="${applicationScope.categorias}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" name="categorias" value="${categoria.idCategoria}" id="categoria${categoria.idCategoria}" ${param.categorias}>
                                            <label class="form-check-label" for="categoria${categoria.idCategoria}">
                                                    ${categoria.nombre}
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <!-- Marcas -->
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingBrand">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseBrand" aria-expanded="false" aria-controls="collapseBrand">
                                    Marcas
                                </button>
                            </h2>
                            <div id="collapseBrand" class="accordion-collapse collapse" aria-labelledby="headingBrand" data-bs-parent="#filterAccordion">
                                <div class="accordion-body">
                                    <c:forEach var="marca" items="${applicationScope.marcas}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" name="marcas" value="${marca}" id="marca${marca}">
                                            <label class="form-check-label" for="marca${marca}">
                                                    ${marca}
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <!-- Precio máximo -->
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingPrice">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapsePrice" aria-expanded="false" aria-controls="collapsePrice">
                                    Precio m&aacute;ximo
                                </button>
                            </h2>
                            <div id="collapsePrice" class="accordion-collapse collapse" aria-labelledby="headingPrice" data-bs-parent="#filterAccordion">
                                <div class="accordion-body">
                                    <label for="precio" class="form-label">Precio m&aacute;ximo</label>
                                    <input type="range" name="precioMax" id="precio" value="2000" class="form-range" min="0" max="2000" step="0.01">
                                    <p>Precio m&aacute;ximo: <span id="precioValue">2000,00 €</span></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </aside>

        <!-- Productos -->
        <article class="col-md-9">
            <div class="row">
                <c:if test="${not empty requestScope.productos}">
                    <c:forEach var="producto" items="${requestScope.productos}" varStatus="status">
                        <div class="col-md-3 mb-4">
                            <div class="card h-100 shadow-sm border-light">
                                <img src="${applicationScope.contexto}/IMG/productos/${producto.imagen}.jpg" class="card-img-top" alt="${producto.nombre}">
                                <div class="card-body flex-column d-flex justify-content-between">
                                    <h5 class="card-title">${producto.nombre}</h5>
                                    <h6 class="card-subtitle mb-2 text-muted">Marca: ${producto.marca}</h6>
                                    <p class="card-text">${producto.descripcion.substring(0, 25)}...</p>
                                    <p class="card-text"><strong>Precio: </strong><fmt:formatNumber value="${producto.precio}" type="currency"/></p>
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#${producto.idProducto}Modal">
                                        Ver detalles
                                    </button>
                                </div>
                            </div>
                        </div>
                        <c:if test="${status.index % 4 == 3}">
                            <div class="w-100"></div>
                        </c:if>

                        <!-- Modal -->
                        <div class="modal fade" id="${producto.idProducto}Modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header bg-primary text-white">
                                        <h3 class="modal-title">${producto.nombre}</h3>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <img src="${applicationScope.contexto}/IMG/productos/${producto.imagen}.jpg" class="img-fluid" alt="${producto.nombre}">
                                            </div>
                                            <div class="col-md-8">
                                                <h5>Marca: ${producto.marca}</h5>
                                                <p>${producto.descripcion}</p>
                                                <p><strong>Precio: </strong><fmt:formatNumber value="${producto.precio}" type="currency" currencySymbol="€" /></p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">Cancelar</button>
                                        <form method="post" action="${applicationScope.contexto}/Carrito">
                                            <button type="submit" name="anadir" value="${producto.idProducto}" class="btn btn-primary"><i class="bi bi-cart"></i> A&ntilde;adir al carrito</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>

                <c:if test="${empty requestScope.productos}">
                    <div class="alert alert-warning m-3" role="alert">
                        No se han encontrado productos que cumplan con los requisitos especificados.
                    </div>
                </c:if>
            </div>
        </article>
    </div>
</main>

<!-- Alertas -->
<c:if test="${requestScope.error != null}">
    <div class="alert alert-danger alert-dismissible fade show position-fixed top-0 end-0 m-3" role="alert" id="errorAlert">
            ${requestScope.error}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <script>
        setTimeout(() => {
            const alertElement = document.getElementById('errorAlert');
            if (alertElement) {
                alertElement.classList.remove('show');
                alertElement.classList.add('fade');
                setTimeout(() => alertElement.remove(), 500);
            }
        }, 2000);
    </script>
</c:if>

<c:if test="${requestScope.succes != null}">
    <div class="alert alert-success alert-dismissible fade show position-fixed top-0 end-0 m-3" role="alert" id="successAlert">
            ${requestScope.succes}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <script>
        setTimeout(() => {
            const alertElement = document.getElementById('successAlert');
            if (alertElement) {
                alertElement.classList.remove('show');
                alertElement.classList.add('fade');
                setTimeout(() => alertElement.remove(), 500);
            }
        }, 2000);
    </script>
</c:if>

<c:import url="INC/footer.jsp"/>
<script src="${bootstrapJS}"></script>
<script src="${javaScript}"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>
</html>
