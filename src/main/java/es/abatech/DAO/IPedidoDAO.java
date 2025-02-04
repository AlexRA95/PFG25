package es.abatech.DAO;

import es.abatech.beans.Pedido;
import es.abatech.beans.Usuario;

import java.util.List;

/**
 * Interfaz para las operaciones de acceso a datos relacionadas con los pedidos.
 */
public interface IPedidoDAO {

    /**
     * Recupera un pedido asociado a un usuario dado.
     *
     * @param usuario el usuario cuyo pedido se desea recuperar
     * @return el pedido asociado al usuario
     */
    public Pedido getPedidoByUser(Usuario usuario);

    /**
     * A&ntilde;ade un nuevo pedido.
     *
     * @param pedido el pedido a a&ntilde;adir
     */
    public void addPedido(Pedido pedido);

    /**
     * Elimina un pedido dado.
     *
     * @param pedido el pedido a eliminar
     */
    public void deletePedido(Pedido pedido);

    /**
     * Actualiza el importe de un pedido dado.
     *
     * @param pedido el pedido cuyo importe se desea actualizar
     */
    public void updateImportePedido(Pedido pedido);

    /**
     * Actualiza un pedido dado.
     *
     * @param pedido el pedido a actualizar
     */
    public void updatePedido(Pedido pedido);

    /**
     * Recupera una lista de pedidos finalizados asociados a un usuario dado.
     *
     * @param usuario el usuario cuyos pedidos finalizados se desean recuperar
     * @return una lista de pedidos finalizados asociados al usuario
     */
    public List<Pedido> getPedidosFinByUser(Usuario usuario);

    /**
     * Cierra la conexi&oacute;n con la base de datos.
     */
    public void closeConnection();
}