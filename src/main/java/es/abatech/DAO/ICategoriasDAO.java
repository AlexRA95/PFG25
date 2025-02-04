package es.abatech.DAO;

import es.abatech.beans.Categoria;

import java.util.List;

/**
 * Interfaz para las operaciones de acceso a datos relacionadas con las categor&iacute;as.
 */
public interface ICategoriasDAO {

    /**
     * Recuperamos todas las categorias con {@link es.abatech.beans.Producto} asociados.
     *
     * @return Una lista de {@link Categoria} que tienen {@link es.abatech.beans.Producto} asociados.
     */
    public List<Categoria> getCategorias();

    /**
     * Cierra la conexi&oacute;n con la base de datos.
     */
    public void closeConnection();
}
