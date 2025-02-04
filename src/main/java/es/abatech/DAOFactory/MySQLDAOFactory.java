package es.abatech.DAOFactory;


import es.abatech.DAO.*;

/**
 * Fábrica concreta para la fuente de datos MySQL
 * @author jesus
 */
public class MySQLDAOFactory extends DAOFactory{

    @Override
    public ICategoriasDAO getCategoriasDAO() {
        return new CategoriasDAO();
    }

    @Override
    public IProductosDAO getProductosDAO() {
        return new ProductosDAO();
    }

    @Override
    public IUsuariosDAO getUsuariosDAO() { return new UsuariosDAO(); }

    @Override
    public IPedidoDAO getPedidoDAO() {
        return new PedidoDAO();
    }

    @Override
    public ILineaPedidosDAO getLineaPedidosDAO() {
        return new LineaPedidosDAO();
    }
}
