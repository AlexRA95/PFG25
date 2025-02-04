package es.abatech.DAO;

import es.abatech.beans.LineaPedido;
import es.abatech.beans.Pedido;

import java.sql.Connection;

/**
 * Interfaz para las operaciones de acceso a datos relacionadas con las l&iacute;neas de pedido.
 */
public interface ILineaPedidosDAO {

    /**
     * A&ntilde;ade una l&iacute;nea de pedido a un pedido dado utilizando una conexi&oacute;n espec&iacute;fica.
     *
     * @param lineaPedido la l&iacute;nea de pedido a a&ntilde;adir
     * @param idPedido el ID del pedido al que se a&ntilde;ade la l&iacute;nea de pedido
     * @param conexion la conexi&oacute;n a la base de datos a utilizar
     */
    public void addLineaPedido(LineaPedido lineaPedido, short idPedido, Connection conexion);

    /**
     * A&ntilde;ade una l&iacute;nea de pedido a un pedido dado.
     *
     * @param lineaPedido la l&iacute;nea de pedido a a&ntilde;adir
     * @param idPedido el ID del pedido al que se a&ntilde;ade la l&iacute;nea de pedido
     */
    public void addLineaPedido(LineaPedido lineaPedido, short idPedido);

    /**
     * Elimina una l&iacute;nea de pedido de un pedido dado.
     *
     * @param pedido el pedido del que se elimina la l&iacute;nea de pedido
     * @param idProducto el ID del producto cuya l&iacute;nea de pedido se elimina
     */
    public void deleteLineaPedido(Pedido pedido, short idProducto);

    /**
     * Reduce la cantidad de un producto en una l&iacute;nea de pedido de un pedido dado.
     *
     * @param pedido el pedido en el que se reduce la cantidad del producto
     * @param idProducto el ID del producto cuya cantidad se reduce
     */
    public void reduceLineaPedido(Pedido pedido, short idProducto);

    /**
     * Aumenta la cantidad de un producto en una l&iacute;nea de pedido de un pedido dado.
     *
     * @param pedido el pedido en el que se aumenta la cantidad del producto
     * @param idProducto el ID del producto cuya cantidad se aumenta
     */
    public void aumentarLineaPedido(Pedido pedido, Integer idProducto);

    /**
     * Cierra la conexi&oacute;n con la base de datos.
     */
    public void closeConnection();
}