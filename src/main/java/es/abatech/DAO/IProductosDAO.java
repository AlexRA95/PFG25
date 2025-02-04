package es.abatech.DAO;

import es.abatech.beans.Filtro;
import es.abatech.beans.Producto;

import java.util.List;

/**
 * Interfaz para las operaciones de acceso a datos relacionadas con los productos.
 */
public interface IProductosDAO {

    /**
     * Recupera una lista de 8 productos aleatorios.
     *
     * @return una lista de 8 productos aleatorios
     */
    public List<Producto> get8productosRand();

    /**
     * Recupera una lista de productos que coinciden con una descripci&oacute;n dada.
     *
     * @param descripcion la descripci&oacute;n de los productos a recuperar
     * @return una lista de productos que coinciden con la descripci&oacute;n dada
     */
    public List<Producto> getProductosByDescripcion(String descripcion);

    /**
     * Recupera una lista de productos que coinciden con un filtro dado.
     *
     * @param filtro el filtro a aplicar para recuperar los productos
     * @return una lista de productos que coinciden con el filtro dado
     */
    public List<Producto> getProductosByFiltro(Filtro filtro);

    /**
     * Recupera una lista de todas las marcas de productos.
     *
     * @return una lista de todas las marcas de productos
     */
    public List<String> getMarcas();

    /**
     * Recupera un producto por su ID.
     *
     * @param idProducto el ID del producto a recuperar
     * @return el producto con el ID dado
     */
    public Producto getProductoById(int idProducto);

    /**
     * Cierra la conexi&oacute;n con la base de datos.
     */
    public void closeConnection();
}