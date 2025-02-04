package es.abatech.controllers;
import es.abatech.DAO.IProductosDAO;
import es.abatech.DAOFactory.DAOFactory;
import es.abatech.beans.Producto;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet para manejar las solicitudes de b&uacute;squeda de productos.
 */
@WebServlet(name = "Buscar", value = "/Buscar")
public class Buscar extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(".").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String URL = null;
        DAOFactory daof = DAOFactory.getDAOFactory();
        IProductosDAO pdao = daof.getProductosDAO();
        List<Producto> productos =null;

        switch (request.getParameter("opcion")){
            case "buscar":
                //Si el usuario quiere buscar se realiza la consulta
                productos = pdao.getProductosByDescripcion(request.getParameter("nombre"));
                request.setAttribute("productos", productos);
                URL= "index.jsp";
                break;
            default:
                URL = ".";
                break;
        }
        request.getRequestDispatcher(URL).forward(request, response);
    }
}