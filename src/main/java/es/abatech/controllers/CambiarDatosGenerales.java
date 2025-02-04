package es.abatech.controllers;

import es.abatech.DAO.IPedidoDAO;
import es.abatech.DAO.IUsuariosDAO;
import es.abatech.DAOFactory.DAOFactory;
import es.abatech.beans.Pedido;
import es.abatech.beans.Usuario;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Servlet para manejar el cambio de los datos generales de un usuario.
 */
@WebServlet(name = "CambiarDatosGenerales", value = "/CambiarDatosGenerales")
public class CambiarDatosGenerales extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(".").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String URL = "JSP/OpcionesPerfil.jsp";
        DAOFactory daof = DAOFactory.getDAOFactory();
        IUsuariosDAO udao = daof.getUsuariosDAO();
        HttpSession sesion = request.getSession();
        Map<String, String[]> parametros = request.getParameterMap();
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        try {
            BeanUtils.populate(usuario, parametros);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        udao.updateUsuarioGen(usuario);
        sesion.setAttribute("usuario", usuario);
        request.setAttribute("succes", "Se han cambiado los datos correctamente");

        if (sesion.getAttribute("carrito") == null){
            DAOFactory factory = DAOFactory.getDAOFactory();
            IPedidoDAO pedidoDAO = factory.getPedidoDAO();
            Pedido carrito = pedidoDAO.getPedidoByUser((Usuario) sesion.getAttribute("usuario"));
            sesion.setAttribute("carrito", carrito);
        }

        request.getRequestDispatcher(URL).forward(request, response);
    }
}