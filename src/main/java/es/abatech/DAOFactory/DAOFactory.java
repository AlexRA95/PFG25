package es.abatech.DAOFactory;

import es.abatech.DAO.*;

public abstract class DAOFactory {

    /**
     * Una clase abstracta por cada tabla de la base de datos
     * @return Inteface de las operaciones a realizar con la tabla
     */
    public abstract ICategoriasDAO getCategoriasDAO();
    public abstract IProductosDAO getProductosDAO();
    public abstract IUsuariosDAO getUsuariosDAO();
    public abstract IPedidoDAO getPedidoDAO();
    public abstract ILineaPedidosDAO getLineaPedidosDAO();

    /**
     * Fábrica abstracta
     * @return Objeto de la fábrica abstracta
     */
    public static DAOFactory getDAOFactory() {
        DAOFactory daof = null;
        daof = new MySQLDAOFactory();
        return daof;
    }

}
