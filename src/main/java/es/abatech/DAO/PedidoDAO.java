package es.abatech.DAO;

import es.abatech.beans.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidoDAO implements IPedidoDAO {

    @Override
    public List<Pedido> getPedidosFinByUser(Usuario usuario) {
        Connection conexion = null;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        String sql = null;
        List<Pedido> pedidos = new ArrayList<>();
        Pedido pedidoActual = null;

        try {
            conexion = ConnectionFactory.getConnection();
            sql = "SELECT p.*, l.*, pr.* FROM pedidos p " +
                    "JOIN abatech.lineaspedidos l ON p.idPedido = l.idPedido " +
                    "JOIN abatech.productos pr ON l.idProducto = pr.idProducto " +
                    "WHERE p.idUsuario = ? AND p.estado = 'f'";
            preparada = conexion.prepareStatement(sql);
            preparada.setInt(1, usuario.getIdUsuario());
            resultado = preparada.executeQuery();
            while (resultado.next()) {
                int idPedido = resultado.getInt("idPedido");
                if (pedidoActual == null || pedidoActual.getIdPedido() != idPedido) {
                    List<LineaPedido> lineas = new ArrayList<>();
                    pedidoActual = new Pedido();
                    pedidoActual.setIdPedido((short) idPedido);
                    pedidoActual.setFecha(resultado.getDate("fecha"));
                    pedidoActual.setEstado(Pedido.Estado.valueOf(resultado.getString("estado")));
                    pedidoActual.setUsuario(usuario);
                    pedidoActual.setImporte(resultado.getDouble("importe"));
                    pedidoActual.setIva(resultado.getDouble("iva"));
                    pedidoActual.setLineasPedido(lineas);
                    pedidos.add(pedidoActual);
                }
                LineaPedido linea = new LineaPedido();
                linea.setIdLinea((short) resultado.getInt("idLinea"));
                linea.setCantidad((byte) resultado.getInt("cantidad"));
                Producto producto = new Producto();
                producto.setIdProducto((short) resultado.getInt("idProducto"));
                producto.setNombre(resultado.getString("nombre"));
                producto.setPrecio(resultado.getDouble("precio"));
                producto.setImagen(resultado.getString("imagen"));
                producto.setMarca(resultado.getString("marca"));
                producto.setDescripcion(resultado.getString("descripcion"));
                linea.setProducto(producto);
                pedidoActual.getLineasPedido().add(linea);
            }
        } catch (SQLException e) {
            Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
        return pedidos;
    }

    @Override
    public void updatePedido(Pedido pedido) {
        Connection conexion = null;
        PreparedStatement preparada = null;
        String sql = "UPDATE pedidos SET estado = ? WHERE idPedido = ?";

        try {
            conexion = ConnectionFactory.getConnection();
            conexion.setAutoCommit(false);
            preparada = conexion.prepareStatement(sql);
            preparada.setString(1, "f");
            preparada.setShort(2, pedido.getIdPedido());
            preparada.executeUpdate();
            conexion.commit();
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, e);
            }

        } finally {
            this.closeConnection();
        }
    }

    @Override
    public void updateImportePedido(Pedido pedido) {
        Connection conexion = null;
        PreparedStatement preparada = null;
        String sql = "UPDATE pedidos SET importe = ?, iva = ? WHERE idPedido = ?";

        try {
            conexion = ConnectionFactory.getConnection();
            conexion.setAutoCommit(false);
            preparada = conexion.prepareStatement(sql);
            preparada.setDouble(1, pedido.getImporte());
            preparada.setDouble(2, pedido.getIva());
            preparada.setShort(3, pedido.getIdPedido());
            preparada.executeUpdate();
            conexion.commit();
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, e);
            }

        } finally {
            this.closeConnection();
        }

    }

    @Override
    public Pedido getPedidoByUser(Usuario usuario) {
        Connection conexion = null;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        String sql = null;
        Pedido pedido = null;
        List<LineaPedido> lineas = new ArrayList<>();
        LineaPedido linea = null;

        try {
            conexion = ConnectionFactory.getConnection();
            sql = "SELECT p.*, l.*, pr.* FROM pedidos p " +
                    "JOIN abatech.lineaspedidos l ON p.idPedido = l.idPedido " +
                    "JOIN abatech.productos pr ON l.idProducto = pr.idProducto " +
                    "WHERE p.idUsuario = ? AND p.estado = 'c'";
            preparada = conexion.prepareStatement(sql);
            preparada.setInt(1, usuario.getIdUsuario());
            resultado = preparada.executeQuery();
            while (resultado.next()) {
                if (pedido == null) {
                    pedido = new Pedido();
                    pedido.setIdPedido((short) resultado.getInt("idPedido"));
                    pedido.setFecha(resultado.getDate("fecha"));
                    pedido.setEstado(Pedido.Estado.valueOf(resultado.getString("estado")));
                    pedido.setUsuario(usuario);
                    pedido.setImporte(resultado.getDouble("importe"));
                    pedido.setIva(resultado.getDouble("iva"));
                }
                linea = new LineaPedido();
                linea.setIdLinea((short) resultado.getInt("idLinea"));
                linea.setCantidad((byte) resultado.getInt("cantidad"));

                Producto producto = new Producto();
                producto.setIdProducto((short) resultado.getInt("idProducto"));
                producto.setNombre(resultado.getString("nombre"));
                producto.setPrecio(resultado.getDouble("precio"));
                producto.setImagen(resultado.getString("imagen"));
                producto.setMarca(resultado.getString("marca"));
                producto.setDescripcion(resultado.getString("descripcion"));
                linea.setProducto(producto);
                lineas.add(linea);
            }
            if (pedido != null) {
                pedido.setLineasPedido(lineas);
            }
        } catch (SQLException e) {
            Logger.getLogger(CategoriasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }

        return pedido;
    }

    @Override
    public void addPedido(Pedido pedido) {
        Connection conexion = null;
        PreparedStatement preparada = null;
        ResultSet generatedKeys = null;
        String sqlPedido = "INSERT INTO pedidos (fecha, estado, idUsuario, importe, iva) VALUES (?, ?, ?, ?, ?)";
        LineaPedidosDAO lineaPedidosDAO = new LineaPedidosDAO();

        try {
            conexion = ConnectionFactory.getConnection();
            conexion.setAutoCommit(false);

            preparada = conexion.prepareStatement(sqlPedido, PreparedStatement.RETURN_GENERATED_KEYS);
            preparada.setDate(1, new java.sql.Date(pedido.getFecha().getTime()));
            preparada.setString(2, pedido.getEstado().name());
            preparada.setInt(3, pedido.getUsuario().getIdUsuario());
            preparada.setDouble(4, pedido.getImporte());
            preparada.setDouble(5, pedido.getIva());
            preparada.executeUpdate();

            generatedKeys = preparada.getGeneratedKeys();
            if (generatedKeys.next()) {
                pedido.setIdPedido(generatedKeys.getShort(1));
            }

            for (LineaPedido linea : pedido.getLineasPedido()) {
                lineaPedidosDAO.addLineaPedido(linea, pedido.getIdPedido(), conexion);
            }

            conexion.commit();
        } catch (SQLException e) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public void deletePedido(Pedido pedido) {
        Connection conexion = null;
        PreparedStatement preparada = null;
        String sql = "DELETE FROM pedidos WHERE idPedido = ?";

        try {
            conexion = ConnectionFactory.getConnection();
            conexion.setAutoCommit(false);
            preparada = conexion.prepareStatement(sql);
            preparada.setShort(1, pedido.getIdPedido());
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
