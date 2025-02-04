/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.abatech.events;

import es.abatech.DAO.ICategoriasDAO;
import es.abatech.DAO.IProductosDAO;
import es.abatech.DAOFactory.DAOFactory;
import es.abatech.beans.Categoria;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * Clase que implementa ServletContextListener para inicializar y destruir el contexto de la aplicaci&oacute;n.
 * Se encarga de cargar las categor&iacute;as y marcas al iniciar la aplicaci&oacute;n y eliminarlas al destruir el contexto.
 *
 * @autor Alejandro Rodr&iacute;guez &Aacute;lvarez
 */
@WebListener
public class ApplicationStart implements ServletContextListener {

    /**
     * M&eacute;todo llamado al inicializar el contexto de la aplicaci&oacute;n.
     * Carga las categor&iacute;as y marcas desde la base de datos y las almacena en el contexto de la aplicaci&oacute;n.
     *
     * @param sce el evento de inicializaci&oacute;n del contexto del servlet
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext contexto = sce.getServletContext();
        DAOFactory daof = DAOFactory.getDAOFactory();
        ICategoriasDAO cdao = daof.getCategoriasDAO();
        IProductosDAO pdao = daof.getProductosDAO();
        List<Categoria> categorias = cdao.getCategorias();
        List<String> marcas = pdao.getMarcas();
        sce.getServletContext().setAttribute("categorias", categorias);
        sce.getServletContext().setAttribute("marcas", marcas);
    }

    /**
     * M&eacute;todo llamado al destruir el contexto de la aplicaci&oacute;n.
     * Elimina las categor&iacute;as y marcas del contexto de la aplicaci&oacute;n.
     *
     * @param sce el evento de destrucci&oacute;n del contexto del servlet
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext contexto = sce.getServletContext();
        sce.getServletContext().removeAttribute("categorias");
        sce.getServletContext().removeAttribute("marcas");
    }

}
