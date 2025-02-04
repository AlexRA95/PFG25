package es.abatech.controllers;
import es.abatech.DAO.IProductosDAO;
import es.abatech.DAOFactory.DAOFactory;
import es.abatech.beans.Filtro;
import es.abatech.beans.Producto;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Servlet para filtrar productos seg&uacute;n criterios especificados.
 */
@WebServlet(name = "FiltrarProds", value = "/FiltrarProds")
public class FiltrarProds extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(".").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daof = DAOFactory.getDAOFactory();
        IProductosDAO pdao = daof.getProductosDAO();
        List<Producto> productos = new ArrayList<>();
        Filtro filtro = new Filtro();

        // Poblar las categorías
        String[] categorias = request.getParameterValues("categorias");
        if (categorias != null) {
            filtro.setCategorias(Arrays.asList(categorias));
        }

        // Poblar el precio máximo
        String precioMax = request.getParameter("precioMax");
        if (precioMax != null && !precioMax.isEmpty()) {
            filtro.setPrecioMax(Double.parseDouble(precioMax));
        }

        // Poblar las marcas
        String[] marcas = request.getParameterValues("marcas");
        if (marcas != null) {
            filtro.setMarcas(Arrays.asList(marcas));
        }

        productos = pdao.getProductosByFiltro(filtro);
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}