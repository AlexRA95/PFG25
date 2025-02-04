package es.abatech.controllers;

import es.abatech.DAO.ILineaPedidosDAO;
import es.abatech.DAO.IPedidoDAO;
import es.abatech.DAOFactory.DAOFactory;
import es.abatech.beans.LineaPedido;
import es.abatech.beans.Pedido;
import es.abatech.models.Utils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Servlet para gestionar el carrito de compras.
 */
@WebServlet(name = "GestionCarrito", value = "/GestionCarrito")
public class GestionCarrito extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(".").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Miramos si el usuario esta logeado o no
        HttpSession session = request.getSession();
        //Recuperamos el carrito de la sesion
        Pedido pedido = (Pedido) session.getAttribute("carrito");
        //Recuperamos el valor de la linea de pedido que se almacena en un input type hidden
        Short idProducto = null;
        if (request.getParameter("idProducto") != null){
            idProducto = Short.valueOf(request.getParameter("idProducto"));
        }
        Boolean logeado = session.getAttribute("usuario") != null;
        //Si el usuario está logeado, los cambios se guardarán tanto en la base de datos como en la sesión
        //Si el usuario no está logeado, los cambios se guardarán en la sesión y en la cookie
        switch (request.getParameter("opcion")){
            //Eliminar linea de pedido
            case "eliminar":
                //Primer comprobamos si hay una linea de pedido en la sesión
                if (pedido.getLineasPedido().size() <= 1){
                    //Si solo hay una linea de pedido, ponemos que el atributo de sesion sea null
                    session.setAttribute("carrito", null);
                    //Ahora, si esta logeado borramos las lineas de pedido de la base de datos y luego el pedido
                    if (logeado){
                        DAOFactory daof = DAOFactory.getDAOFactory();
                        IPedidoDAO pdao = daof.getPedidoDAO();
                        pdao.deletePedido(pedido);
                    }else{
                        //Ahora eliminamos la cookie
                        Cookie cookie = Utils.buscarCookie("carrito",request.getCookies());
                        cookie.setMaxAge(0);//Borramos la cookie
                        response.addCookie(cookie);
                    }
                }else{
                    //Si hay mas de una linea de pedido, eliminamos la linea de pedido
                    Utils.borrarLineaPedido(idProducto,pedido);
                    //Si esta logeado, eliminamos la linea de pedido de la base de datos
                    if (logeado){
                        DAOFactory daof = DAOFactory.getDAOFactory();
                        ILineaPedidosDAO ldao = daof.getLineaPedidosDAO();
                        IPedidoDAO pdao = daof.getPedidoDAO();
                        ldao.deleteLineaPedido(pedido,idProducto);
                        pdao.updateImportePedido(pedido);
                    }else{
                        //Pasamos el contenido del pedido a la cookie
                        Cookie cookie = new Cookie("carrito", URLEncoder.encode(Utils.pedidoToCookie(pedido), "UTF-8"));
                        cookie.setMaxAge(60*60*24*2);//Hacemos que la cookie dure 2 dias
                        response.addCookie(cookie);
                    }
                }
            break;
            //Restar cantidad de producto
            case "decrementar":
                // Primero miramos si la cantidad es 1
                // Si la cantidad es 1, eliminamos la línea de pedido
                if (pedido.getLineasPedido().size() == 1 && pedido.getLineasPedido().get(0).getCantidad() == 1) {
                    // Si solo hay una línea de pedido, ponemos que el atributo de sesión sea null
                    session.setAttribute("carrito", null);
                    // Ahora, si está logeado borramos las líneas de pedido de la base de datos y luego el pedido
                    if (logeado) {
                        DAOFactory daof = DAOFactory.getDAOFactory();
                        IPedidoDAO pdao = daof.getPedidoDAO();
                        pdao.deletePedido(pedido);
                    } else {
                        // Ahora eliminamos la cookie
                        Cookie cookie = Utils.buscarCookie("carrito", request.getCookies());
                        cookie.setMaxAge(0); // Borramos la cookie
                        response.addCookie(cookie);
                    }
                } else {
                    // Buscamos la línea de pedido correspondiente al producto
                    LineaPedido lineaPedido = null;
                    for (LineaPedido lp : pedido.getLineasPedido()) {
                        if (lp.getProducto().getIdProducto().equals(idProducto)) {
                            lineaPedido = lp;
                            break;
                        }
                    }

                    if (lineaPedido != null) {
                        if (lineaPedido.getCantidad() == 1) {
                            // Si la cantidad es 1, eliminamos la línea de pedido
                            Utils.borrarLineaPedido(idProducto, pedido);
                            // Si está logeado, eliminamos la línea de pedido de la base de datos
                            if (logeado) {
                                DAOFactory daof = DAOFactory.getDAOFactory();
                                ILineaPedidosDAO ldao = daof.getLineaPedidosDAO();
                                IPedidoDAO pdao = daof.getPedidoDAO();
                                ldao.deleteLineaPedido(pedido, idProducto);
                                pdao.updateImportePedido(pedido);
                            } else {
                                // Pasamos el contenido del pedido a la cookie
                                Cookie cookie = new Cookie("carrito", URLEncoder.encode(Utils.pedidoToCookie(pedido), "UTF-8"));
                                cookie.setMaxAge(60 * 60 * 24 * 2); // Hacemos que la cookie dure 2 días
                                response.addCookie(cookie);
                            }
                        } else {
                            // Si la cantidad es mayor que 1, restamos la cantidad
                            Utils.restarCantidadProducto(idProducto, pedido);
                            // Si está logeado, restamos la cantidad de la línea de pedido de la base de datos
                            if (logeado) {
                                DAOFactory daof = DAOFactory.getDAOFactory();
                                ILineaPedidosDAO ldao = daof.getLineaPedidosDAO();
                                IPedidoDAO pdao = daof.getPedidoDAO();
                                ldao.reduceLineaPedido(pedido, idProducto);
                                pdao.updateImportePedido(pedido);
                            } else {
                                // Pasamos el contenido del pedido a la cookie
                                Cookie cookie = new Cookie("carrito", URLEncoder.encode(Utils.pedidoToCookie(pedido), "UTF-8"));
                                cookie.setMaxAge(60 * 60 * 24 * 2); // Hacemos que la cookie dure 2 días
                                response.addCookie(cookie);
                            }
                        }
                    }
                }
                break;
            case "incrementar":
                //Si el usuario está logeado,
                Utils.aumentarCantidadProducto(idProducto, pedido);
                if (logeado) {
                    DAOFactory daof = DAOFactory.getDAOFactory();
                    ILineaPedidosDAO ldao = daof.getLineaPedidosDAO();
                    IPedidoDAO pdao = daof.getPedidoDAO();
                    ldao.aumentarLineaPedido(pedido, Integer.valueOf(idProducto));
                    pdao.updateImportePedido(pedido);
                } else {
                    // Pasamos el contenido del pedido a la cookie
                    Cookie cookie = new Cookie("carrito", URLEncoder.encode(Utils.pedidoToCookie(pedido), "UTF-8"));
                    cookie.setMaxAge(60 * 60 * 24 * 2); // Hacemos que la cookie dure 2 días
                    response.addCookie(cookie);
                }
                break;
            case "comprar":
                //Si el usuario quiere comprar el pedido, cambiamos el estado del pedido de "c" a "f"
                DAOFactory daof = DAOFactory.getDAOFactory();
                IPedidoDAO pdao = daof.getPedidoDAO();
                pdao.updatePedido(pedido);
                //Ahora, borraos el pedido de la sesion
                session.setAttribute("carrito", null);
                request.setAttribute("succes", "Compra realizada con éxito");
                break;
            case "vaciar":
                //Si el usuario está logeado, borramos las lineas de pedido de la base de datos y luego el pedido
                //Si solo hay una linea de pedido, ponemos que el atributo de sesion sea null
                session.setAttribute("carrito", null);
                //Ahora, si esta logeado borramos las lineas de pedido de la base de datos y luego el pedido
                if (logeado){
                    daof = DAOFactory.getDAOFactory();
                    pdao = daof.getPedidoDAO();
                    pdao.deletePedido(pedido);
                }else{
                    //Ahora eliminamos la cookie
                    Cookie cookie = Utils.buscarCookie("carrito",request.getCookies());
                    cookie.setMaxAge(0);//Borramos la cookie
                    response.addCookie(cookie);
                    request.setAttribute("error", "Se han eliminado todos los productos del carrito");
                }

                break;

        }

        request.getRequestDispatcher("JSP/Carrito.jsp").forward(request, response);
    }
}