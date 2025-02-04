<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="es">
<head>
    <jsp:include page="../INC/metas.jsp">
        <jsp:param name="titulo" value="Abatech - Perfil Usuario" />
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
    <div class="row g-4">
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h2 class="card-title">Cambiar Datos Generales</h2>
                    <form action="${applicationScope.contexto}/CambiarDatosGenerales" id="datosGenerales" method="post">
                        <div class="mb-3">
                            <label for="nombre" class="form-label">Nombre</label>
                            <input type="text" class="form-control" id="nombre" name="nombre" value="${sessionScope.usuario.nombre}">
                        </div>
                        <div class="mb-3">
                            <label for="apellidos" class="form-label">Apellidos</label>
                            <input type="text" class="form-control" id="apellidos" name="apellidos" value="${sessionScope.usuario.apellidos}">
                        </div>
                        <div class="mb-3">
                            <label for="telefono" class="form-label">Teléfono</label>
                            <input type="tel" class="form-control" id="telefono" name="telefono" value="${sessionScope.usuario.telefono}">
                        </div>
                        <div class="mb-3">
                            <label for="direccion" class="form-label">Dirección</label>
                            <input type="text" class="form-control" id="direccion" name="direccion" value="${sessionScope.usuario.direccion}">
                        </div>
                        <div class="mb-3">
                            <label for="codigoPostal" class="form-label">Código Postal</label>
                            <input type="text" class="form-control" id="codigoPostal" name="codigoPostal" value="0${sessionScope.usuario.codigoPostal}">
                        </div>
                        <div class="mb-3">
                            <label for="localidad" class="form-label">Localidad</label>
                            <input type="text" class="form-control" id="localidad" name="localidad" value="${sessionScope.usuario.localidad}">
                        </div>
                        <div class="mb-3">
                            <label for="provincia" class="form-label">Provincia</label>
                            <input type="text" class="form-control" id="provincia" name="provincia" value="${sessionScope.usuario.provincia}">
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Cambiar Datos Generales</button>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h2 class="card-title">Cambiar Contraseña</h2>
                    <form action="${contexto}/CambiarContrasenia" method="post">
                        <div class="mb-3">
                            <label for="contraseniaActual" class="form-label">Contraseña Actual</label>
                            <input type="password" class="form-control" id="contraseniaActual" name="contraseniaActual">
                        </div>
                        <div class="mb-3">
                            <label for="nuevaContrasenia" class="form-label">Nueva Contraseña</label>
                            <input type="password" class="form-control" id="nuevaContrasenia" name="nuevaContrasenia">
                        </div>
                        <div class="mb-3">
                            <label for="ConfNuevaContrasenia" class="form-label">Confirmar nueva contraseña</label>
                            <input type="password" class="form-control" id="ConfNuevaContrasenia" name="ConfNuevaContrasenia">
                        </div>
                        <button type="submit" class="btn btn-primary w-100" disabled>Cambiar Contraseña</button>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h2 class="card-title">Cambiar Avatar</h2>
                    <form action="${contexto}/CambiarAvatar" method="post" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label for="avatar" class="form-label">Avatar</label>
                            <input type="file" class="form-control" id="avatar" name="avatar">
                            <img id="avatarPreview" src="#" alt="Vista previa del avatar" class="img-thumbnail mt-3" style="display: none; max-width: 100px; max-height: 100px;">
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Cambiar Avatar</button>
                    </form>
                </div>
            </div>
        </div>

        <script>
            document.getElementById('avatar').addEventListener('change', function(event) {
                const file = event.target.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function(e) {
                        const preview = document.getElementById('avatarPreview');
                        preview.src = e.target.result;
                        preview.style.display = 'block';
                    };
                    reader.readAsDataURL(file);
                }
            });
        </script>
    </div>
</main>

<!-- Alerta de error -->
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

<!-- Alerta de éxito -->
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

<c:import url="../INC/footer.jsp"/>
<script src="${bootstrapJS}"></script>
<script src="${javaScript}"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>
</html>
