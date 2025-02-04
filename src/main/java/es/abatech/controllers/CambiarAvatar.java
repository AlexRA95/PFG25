package es.abatech.controllers;

import es.abatech.DAO.IPedidoDAO;
import es.abatech.DAO.IUsuariosDAO;
import es.abatech.DAOFactory.DAOFactory;
import es.abatech.beans.Pedido;
import es.abatech.beans.Usuario;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Servlet para manejar el cambio de avatar de un usuario.
 */
@MultipartConfig
@WebServlet(name = "CambiarAvatar", value = "/CambiarAvatar")
public class CambiarAvatar extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(".").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String URL = "JSP/OpcionesPerfil.jsp";
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        //Verificamos si se ha mandado un archivo
        if (request.getPart("avatar") != null){
            //Comproabamos si el archivo es una imagen png
            if (!request.getPart("avatar").getContentType().equals("image/png")){
                request.setAttribute("error", "El archivo debe ser una imagen png");
            }else if (request.getPart("avatar").getSize() > 500*1024){
                //Comprobamos si el archivo es mayor de 500KB
                request.setAttribute("error", "El archivo no puede ser mayor de 1MB");
            }else {
                //Si se ha mandado un archivo, lo guardamos en la carpeta de avatares
                Part filePart = request.getPart("avatar");
                String fileName = "Avatar_" + usuario.getIdUsuario() + ".png";
                String path = getServletContext().getRealPath("IMG/avatars") + "/" + fileName;
                filePart.write(path);
                //Actualizamos el avatar del usuario
                usuario.setAvatar(fileName);
                //Actualizamos el avatar en la base de datos
                DAOFactory daof = DAOFactory.getDAOFactory();
                IUsuariosDAO udao = daof.getUsuariosDAO();
                udao.updateUsuarioAvatar(usuario);
                request.setAttribute("succes", "Se ha cambiado el avatar correctamente");
            }
        }else{
            //Si no se ha mandado un archivo, mostramos un mensaje de error
            request.setAttribute("error", "No se ha seleccionado ningún archivo");
        }

        if (sesion.getAttribute("carrito") == null){
            DAOFactory factory = DAOFactory.getDAOFactory();
            IPedidoDAO pedidoDAO = factory.getPedidoDAO();
            Pedido carrito = pedidoDAO.getPedidoByUser((Usuario) sesion.getAttribute("usuario"));
            sesion.setAttribute("carrito", carrito);
        }

        request.getRequestDispatcher(URL).forward(request, response);
    }
}