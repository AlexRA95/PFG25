<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<header class="p-3 bg-light text-dark mb-3 border-bottom shadow-sm">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <!-- Logo -->
            <form method="post" action="${applicationScope.contexto}/Frontcontroller" class="col-12 col-lg-auto mb-3 mb-lg-0 text-center me-3">
                <button type="submit" name="opcion" value="nada" class="btn border-0 bg-transparent p-0">
                    <img class="img-fluid" src="${applicationScope.contexto}/IMG/logo/Abatech.png" alt="Abatech Logo" style="max-height: 80px;">
                </button>
            </form>
            
            <!-- Botones de perfil y salir -->
            <form action="${applicationScope.contexto}/OpcionesUser" method="post">
                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0 me-3">
                    <li class="nav-item me-3">
                        <button type="submit" name="opcion" value="perfil" class="btn btn-link nav-link">Perfil</button>
                    </li>
                    <li class="nav-item me-3">
                        <button type="submit" name="opcion" value="pedidos" class="btn btn-link nav-link">Ver pedidos</button>
                    </li>
                    <li class="nav-item me-3">
                        <button type="button" name="opcion" data-bs-toggle="modal" data-bs-target="#confCerrar" value="salir" class="btn btn-link nav-link">Salir</button>
                    </li>
                </ul>
            </form>

            <!-- Barra de búsqueda -->
            <form method="post" action="${applicationScope.contexto}/Buscar" class="col-12 col-lg mb-3 mb-lg-0 me-3">
                <div class="input-group">
                    <input
                            id="searchHeader"
                            name="nombre"
                            type="search"
                            class="form-control rounded-pill"
                            placeholder="Buscar..."
                            aria-label="Buscar">
                    <button
                            class="btn btn-outline-success rounded-pill ms-2"
                            name="opcion"
                            value="buscar"
                            type="submit"
                            id="searchBtnHeader"
                            disabled>
                        Buscar
                    </button>
                </div>
            </form>

            <div class="d-flex align-items-center">
                <a href="#" class="d-block link-body-emphasis text-decoration-none me-3">
                    <img src="${applicationScope.contexto}/IMG/avatars/${sessionScope.usuario.avatar}" alt="mdo" width="32" height="32" class="rounded-circle">
                </a>
                <div>
                    <p class="mb-0">${sessionScope.usuario.nombre} ${sessionScope.usuario.apellidos}</p>
                    <p class="mb-0">
                        &Uacute;ltima conexi&oacute;n: <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${sessionScope.usuario.ultimoAcceso}" />
                    </p>
                </div>
            </div>
            <form method="post" action="${applicationScope.contexto}/OpcionesUser" class="col-12 col-lg-auto d-flex align-items-center">
                <button type="submit" name="opcion" value="carrito" class="btn btn-outline-primary me-2 d-flex justify-content-between align-items-center">
                    <i class="bi bi-cart"></i>
                    <c:set var="totalCantidad" value="0" />
                    <c:forEach var="linea" items="${sessionScope.carrito.lineasPedido}">
                        <c:set var="totalCantidad" value="${totalCantidad + linea.cantidad}" />
                    </c:forEach>
                    <c:choose>
                        <c:when test="${totalCantidad > 0}">
                            <span class="badge bg-primary">${totalCantidad}</span>
                        </c:when>
                    </c:choose>
                </button>
            </form>
        </div>
    </div>
</header>

<!-- Modal para confirmar cerrar sesion -->
<div class="modal fade" id="confCerrar" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="${applicationScope.contexto}/RegistroLogin" method="post" class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Confirmar cierre de sesi&oacute;n</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>&iquest;Est&aacute;s seguro de que deseas cerrar sesi&oacute;n?</p>
            </div>
            <div class="modal-footer">
                <button type="submit" name="opcion" value="salir" class="btn btn-danger">Cerrar sesi&oacute;n</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
            </div>
        </form>
    </div>
</div>