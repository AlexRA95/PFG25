package es.abatech.controllers;

import es.abatech.DAO.IPedidoDAO;
import es.abatech.DAO.IProductosDAO;
import es.abatech.DAO.ProductosDAO;
import es.abatech.DAOFactory.DAOFactory;
import es.abatech.beans.Pedido;
import es.abatech.beans.Producto;
import es.abatech.beans.Usuario;
import es.abatech.models.Utils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet para manejar la p&aacute;gina principal y la gesti&oacute;n del carrito de compras.
 */
@WebServlet(name = "Frontcontroller", value = "/Frontcontroller")
public class Frontcontroller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Hacemos una consulta para obtener 8 productos aleatorios
        DAOFactory daof = DAOFactory.getDAOFactory();
        HttpSession session = request.getSession();
        IProductosDAO pdao = daof.getProductosDAO();
        List<Producto> productos = new ArrayList<>();
        productos = pdao.get8productosRand();
        request.setAttribute("productos", productos);

        //Una vez hecho esto, miramos si existe una cookie con el carrito del usuario
        //Si existe metemos su contenido en un pedido y lo metemos en sesion
        //Si no existe creamos un pedido vacio y lo metemos en sesion
        Cookie cookie = null;
        Pedido pedido = (Pedido) session.getAttribute("carrito");
        cookie = Utils.buscarCookie("carrito", request.getCookies());
        if (cookie != null) {
            // Añadimos contenido de la cookie a la sesion
            pedido = new Pedido();
            Utils.cookieToPedido(URLDecoder.decode(cookie.getValue(), "UTF-8"),pedido);
            session.setAttribute("carrito", pedido);
        }else{
            // Si no existe la cookie, creamos la sesion vacia
            session.setAttribute("carrito", null);
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Hacemos una consulta para obtener 8 productos aleatorios
        DAOFactory daof = DAOFactory.getDAOFactory();
        HttpSession session = request.getSession();
        IProductosDAO pdao = daof.getProductosDAO();
        List<Producto> productos = new ArrayList<>();
        productos = pdao.get8productosRand();
        request.setAttribute("productos", productos);

        if (session.getAttribute("usuario") != null  && session.getAttribute("carrito") == null){
            DAOFactory factory = DAOFactory.getDAOFactory();
            IPedidoDAO pedidoDAO = factory.getPedidoDAO();
            Pedido carrito = pedidoDAO.getPedidoByUser((Usuario) session.getAttribute("usuario"));
            session.setAttribute("carrito", carrito);
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}