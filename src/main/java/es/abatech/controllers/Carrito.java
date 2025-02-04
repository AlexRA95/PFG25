package es.abatech.controllers;

import es.abatech.DAO.ILineaPedidosDAO;
import es.abatech.DAO.IPedidoDAO;
import es.abatech.DAO.IProductosDAO;
import es.abatech.DAOFactory.DAOFactory;
import es.abatech.beans.Pedido;
import es.abatech.beans.Producto;
import es.abatech.beans.Usuario;
import es.abatech.models.Utils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
/**
 * Servlet para manejar el carrito de compras.
 */
@WebServlet(name = "Carrito", value = "/Carrito")
public class Carrito extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(".").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cookie cookie = null;
        Boolean primerPedido = false;
        Pedido pedido = (Pedido) session.getAttribute("carrito");
        Producto producto = null;
        DAOFactory daof = DAOFactory.getDAOFactory();
        IProductosDAO pdao = daof.getProductosDAO();
        IPedidoDAO pedao = daof.getPedidoDAO();
        ILineaPedidosDAO lpdao = daof.getLineaPedidosDAO();
        if (pedido == null) {
            pedido = new Pedido();
            pedido.setIva(21.0);
            pedido.setFecha(new Date());
            pedido.setEstado(Pedido.Estado.c);
            session.setAttribute("carrito", pedido);
            primerPedido = true;
        }
        Integer idProducto = Integer.valueOf(request.getParameter("anadir"));
        producto = pdao.getProductoById(idProducto);
        Boolean yaEsta = Utils.addProductoToPedido(producto, pedido);
        Utils.updateImportePedido(pedido);
        if (session.getAttribute("usuario") == null){
            //Si el usuario no está logeado, metemos el contenido del carrito en la cookie
            //Pasamos el contenido del pedido a la cookie
            cookie = new Cookie("carrito", URLEncoder.encode(Utils.pedidoToCookie(pedido), "UTF-8"));
            cookie.setMaxAge(60*60*24*2);//Hacemos que la cookie dure 2 dias
            response.addCookie(cookie);
        }else{
            if (primerPedido){
                //Si es el primer pedido, creamos un pedido en la BBDD y le añadimos el pedido que hemos generado
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                pedido.setUsuario(usuario);
                pedao.addPedido(pedido);
            }else{
                if (yaEsta){
                    //Si el producto ya estaba en el carrito, aumentamos la cantidad
                    lpdao.aumentarLineaPedido(pedido, idProducto);
                }else {
                    //Si el producto no estaba en el carrito, lo añadimos
                    lpdao.addLineaPedido(pedido.getLineasPedido().get(pedido.getLineasPedido().size()-1), pedido.getIdPedido());
                }
                pedao.updateImportePedido(pedido);
            }
        }

        request.setAttribute("succes","Producto añadido al carrito");


        request.getRequestDispatcher(".").forward(request, response);
    }
}