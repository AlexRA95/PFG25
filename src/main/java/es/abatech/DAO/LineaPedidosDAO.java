package es.abatech.DAO;

import es.abatech.beans.LineaPedido;
import es.abatech.beans.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LineaPedidosDAO implements ILineaPedidosDAO {

    @Override
    public void addLineaPedido(LineaPedido linea, short idPedido, Connection conexion) {
        PreparedStatement preparada = null;
        ResultSet generatedKeys = null;
        String sql = "INSERT INTO lineaspedidos (idPedido, idProducto, cantidad) VALUES (?, ?, ?)";

        try {
            preparada = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparada.setShort(1, idPedido);
            preparada.setShort(2, linea.getProducto().getIdProducto());
            preparada.setByte(3, linea.getCantidad());
            preparada.executeUpdate();

            generatedKeys = preparada.getGeneratedKeys();
            if (generatedKeys.next()) {
                linea.setIdLinea(generatedKeys.getShort(1));
            }
        } catch (SQLException e) {
            Logger.getLogger(LineaPedidosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (generatedKeys != null) {
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    Logger.getLogger(LineaPedidosDAO.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            if (preparada != null) {
                try {
                    preparada.close();
                } catch (SQLException e) {
                    Logger.getLogger(LineaPedidosDAO.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

    @Override
    public void aumentarLineaPedido(Pedido pedido, Integer idProducto) {
        Connection conexion = null;
        PreparedStatement preparada = null;
        String sql = "UPDATE lineaspedidos SET cantidad = cantidad + 1 WHERE idPedido = ? AND idProducto = ?";

        try {
            conexion = ConnectionFactory.getConnection();
            conexion.setAutoCommit(false);
            preparada = conexion.prepareStatement(sql);
            preparada.setShort(1, pedido.getIdPedido());
            preparada.setInt(2, idProducto);
            preparada.executeUpdate();
            conexion.commit();
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public void reduceLineaPedido(Pedido pedido, short idProducto) {
        Connection conexion = null;
        PreparedStatement preparada = null;
        String sql = "UPDATE lineaspedidos SET cantidad = cantidad - 1 WHERE idPedido = ? AND idProducto = ? AND cantidad > 1";

        try {
            conexion = ConnectionFactory.getConnection();
            conexion.setAutoCommit(false);
            preparada = conexion.prepareStatement(sql);
            preparada.setShort(1, pedido.getIdPedido());
            preparada.setShort(2, idProducto);
            preparada.executeUpdate();
            conexion.commit();
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public void addLineaPedido(LineaPedido lineaPedido, short idPedido) {
        Connection conexion = ConnectionFactory.getConnection();
        addLineaPedido(lineaPedido, idPedido, conexion);
        this.closeConnection();
    }

    @Override
    public void deleteLineaPedido(Pedido pedido, short idProducto) {
        Connection conexion = null;
        PreparedStatement preparada = null;
        String sql = "DELETE FROM lineaspedidos WHERE idPedido = ? AND idProducto = ?";

        try {
            conexion = ConnectionFactory.getConnection();
            conexion.setAutoCommit(false);
            preparada = conexion.prepareStatement(sql);
            preparada.setShort(1, pedido.getIdPedido());
            preparada.setShort(2, idProducto);
            preparada.executeUpdate();
            conexion.commit();
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public void closeConnection() {
        ConnectionFactory.closeConexion();
    }

}
