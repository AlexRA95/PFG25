package es.abatech.controllers;

import es.abatech.DAO.IPedidoDAO;
import es.abatech.DAO.IUsuariosDAO;
import es.abatech.DAOFactory.DAOFactory;
import es.abatech.beans.Pedido;
import es.abatech.beans.Usuario;
import es.abatech.models.Utils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Map;

/**
 * Servlet para manejar el registro y el inicio de sesi&oacute;n de los usuarios.
 */
@WebServlet(name = "RegistroLogin", value = "/RegistroLogin")
public class RegistroLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(".").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daof = DAOFactory.getDAOFactory();
        IUsuariosDAO udao = daof.getUsuariosDAO();
        IPedidoDAO pdao = daof.getPedidoDAO();
        String URL = ".";
        Boolean error = false;
        Usuario usuario = new Usuario();
        HttpSession sesion = request.getSession();
        Map<String, String[]> parametros = request.getParameterMap();
        if (request.getParameter("opcion").equals("salir")){
            sesion.removeAttribute("usuario");
            // Eliminamos el atributo de sesion de carrito si existe
            if (sesion.getAttribute("carrito") != null) {
                sesion.removeAttribute("carrito");
            }
        }else {
            switch (request.getParameter("opcion")){
                case "Registrar":
                    try {
                        BeanUtils.populate(usuario, parametros);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                    if(request.getParameter("avatar") == null){
                        usuario.setAvatar("default.png");
                    }
                    usuario.setUltimoAcceso(new Timestamp(System.currentTimeMillis()));
                    udao.createUsuario(usuario);
                    //Volvemos a darle datos al usuario para que este tenga su idUsuario de la base de datos
                    usuario = udao.getUsusarioByEmailPassword(usuario.getEmail(), usuario.getPassword());
                    sesion.setAttribute("usuario", usuario);
                    break;
                case "Iniciar":
                    usuario = udao.getUsusarioByEmailPassword(request.getParameter("email"), request.getParameter("password"));
                    //Si el usuario existe, actualizamos su último acceso
                    //Si no existe, se queda en null y se manda un mensaje de error
                    if(usuario.getIdUsuario() != null){
                        udao.updateUltimaConex(new Timestamp(System.currentTimeMillis()), usuario.getIdUsuario());
                        sesion.setAttribute("usuario", usuario);
                    }else{
                        request.setAttribute("error", "Usuario o contraseña incorrectos");
                        error = true;
                    }
                    break;
            }
            //Comprobamos si el usuario se ha logeado correctamente, si no se redirecciona con un error
            if (!error){
                //Una vez registrado o logeado, miramos si hay algo en el atributo de sesion de carrito
                if(sesion.getAttribute("carrito") != null){
                    //Si hay algo, comprobamos en la BBDD si el usuario tiene un carrito guardado
                    Pedido pedido = pdao.getPedidoByUser(usuario);
                    if(pedido != null){
                        //Si lo tiene, cambiamos la sesion por el carrito de la BBDD
                        sesion.setAttribute("carrito", pedido);
                    }else{
                        //Si no lo tiene, guardamos el carrito de la sesion en la BBDD
                        pedido = (Pedido) sesion.getAttribute("carrito");
                        pedido.setUsuario(usuario);
                        pdao.addPedido(pedido);
                    }
                    //Haya algo en la BBDD o no, eliminamos la cookie al usuario
                    Cookie cookie = Utils.buscarCookie("carrito",request.getCookies());
                    if(cookie != null){
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }else{
                    //Si no hay nada en la sesion, comprobamos si el usuario tiene un carrito en la BBDD
                    Pedido pedido = pdao.getPedidoByUser(usuario);
                    if(pedido != null){
                        //Si lo tiene, cambiamos la sesion por el carrito de la BBDD
                        sesion.setAttribute("carrito", pedido);
                    }
                }
                request.setAttribute("succes", "Has iniciado sesi&oacute;n correctamente");
            }
        }

        request.getRequestDispatcher(URL).forward(request, response);
    }
}