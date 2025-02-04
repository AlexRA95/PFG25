package es.abatech.DAO;

import es.abatech.beans.Categoria;
import es.abatech.beans.Filtro;
import es.abatech.beans.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductosDAO implements IProductosDAO {
    @Override
    public List<Producto> get8productosRand() {
        Connection conexion = null;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        String sql = null;
        Producto producto = null;
        Categoria categoria = null;
        List<Producto> productos = null;

        try {
            conexion = ConnectionFactory.getConnection();
            sql = "Select p.*, c.*\n" +
                    "from productos as p\n" +
                    "JOIN abatech.categorias c on p.idCategoria = c.idCategoria\n" +
                    "ORDER BY RAND( ) LIMIT 8;";
            preparada = conexion.prepareStatement(sql);
            resultado = preparada.executeQuery();
            productos = new ArrayList<>();
            while (resultado.next()) {
                producto = new Producto();
                producto.setIdProducto(resultado.getShort("p.idProducto"));
                producto.setNombre(resultado.getString("p.nombre"));
                producto.setDescripcion(resultado.getString("p.descripcion"));
                producto.setMarca(resultado.getString("p.marca"));
                producto.setPrecio(resultado.getDouble("p.precio"));
                producto.setImagen(resultado.getString("p.imagen"));
                categoria = new Categoria();
                categoria.setNombre(resultado.getString("c.nombre"));
                producto.setCategoria(categoria);
                productos.add(producto);
            }
        } catch (SQLException e) {
            Logger.getLogger(CategoriasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
        return productos;
    }

    @Override
    public Producto getProductoById(int idProducto) {
        Connection conexion = null;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        String sql = null;
        Producto producto = null;
        Categoria categoria = null;

        try {
            conexion = ConnectionFactory.getConnection();
            sql = "Select p.*, c.*\n" +
                    "from productos as p\n" +
                    "JOIN abatech.categorias c on p.idCategoria = c.idCategoria\n" +
                    "WHERE p.idProducto = ?";
            preparada = conexion.prepareStatement(sql);
            preparada.setInt(1, idProducto);
            resultado = preparada.executeQuery();
            if (resultado.next()) {
                producto = new Producto();
                producto.setIdProducto(resultado.getShort("p.idProducto"));
                producto.setNombre(resultado.getString("p.nombre"));
                producto.setDescripcion(resultado.getString("p.descripcion"));
                producto.setMarca(resultado.getString("p.marca"));
                producto.setPrecio(resultado.getDouble("p.precio"));
                producto.setImagen(resultado.getString("p.imagen"));
                categoria = new Categoria();
                categoria.setNombre(resultado.getString("c.nombre"));
                producto.setCategoria(categoria);
            }
        } catch (SQLException e) {
            Logger.getLogger(CategoriasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
        return producto;
    }

    @Override
    public List<Producto> getProductosByDescripcion(String descripcion) {
        Connection conexion = null;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        String sql = null;
        Producto producto = null;
        Categoria categoria = null;
        List<Producto> productos = null;

        try {
            conexion = ConnectionFactory.getConnection();
            sql = "Select p.*, c.*\n" +
                    "from productos as p JOIN abatech.categorias c on p.idCategoria = c.idCategoria\n" +
                    "WHERE p.descripcion LIKE ?";
            preparada = conexion.prepareStatement(sql);
            preparada.setString(1, "%" + descripcion + "%");
            resultado = preparada.executeQuery();
            productos = new ArrayList<>();
            while (resultado.next()) {
                producto = new Producto();
                producto.setIdProducto(resultado.getShort("p.idProducto"));
                producto.setNombre(resultado.getString("p.nombre"));
                producto.setDescripcion(resultado.getString("p.descripcion"));
                producto.setMarca(resultado.getString("p.marca"));
                producto.setPrecio(resultado.getDouble("p.precio"));
                producto.setImagen(resultado.getString("p.imagen"));
                categoria = new Categoria();
                categoria.setNombre(resultado.getString("c.nombre"));
                producto.setCategoria(categoria);
                productos.add(producto);
            }
        } catch (SQLException e) {
            Logger.getLogger(CategoriasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
        return productos;
    }

    @Override
    public List<String> getMarcas() {
        Connection conexion = null;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        String sql = null;
        List<String> marcas = null;

        try {
            conexion = ConnectionFactory.getConnection();
            sql = "SELECT DISTINCT marca FROM productos ORDER BY marca ASC";
            preparada = conexion.prepareStatement(sql);
            resultado = preparada.executeQuery();
            marcas = new ArrayList<>();
            while (resultado.next()) {
                marcas.add(resultado.getString("marca"));
            }
        } catch (SQLException e) {
            Logger.getLogger(CategoriasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
        return marcas;
    }

    @Override
    public List<Producto> getProductosByFiltro(Filtro filtro) {
        Connection conexion = null;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        StringBuilder sql = new StringBuilder("SELECT p.*, c.* FROM productos AS p JOIN abatech.categorias c ON p.idCategoria = c.idCategoria WHERE 1=1");
        Producto producto = null;
        Categoria categoria = null;
        List<Producto> productos = new ArrayList<>();

        try {
            conexion = ConnectionFactory.getConnection();

            if (!filtro.getMarcas().isEmpty()) {
                sql.append(" AND p.marca IN (");
                for (int i = 0; i < filtro.getMarcas().size(); i++) {
                    sql.append("'").append(filtro.getMarcas().get(i)).append("'");
                    if (i < filtro.getMarcas().size() - 1) {
                        sql.append(", ");
                    }
                }
                sql.append(")");
            }

            if (!filtro.getCategorias().isEmpty()) {
                sql.append(" AND p.idCategoria IN (");
                for (int i = 0; i < filtro.getCategorias().size(); i++) {
                    sql.append(filtro.getCategorias().get(i));
                    if (i < filtro.getCategorias().size() - 1) {
                        sql.append(", ");
                    }
                }
                sql.append(")");
            }

            if (filtro.getPrecioMax() != null) {
                sql.append(" AND p.precio <= ").append(filtro.getPrecioMax());
            }

            preparada = conexion.prepareStatement(sql.toString());
            resultado = preparada.executeQuery();

            while (resultado.next()) {
                producto = new Producto();
                producto.setIdProducto(resultado.getShort("p.idProducto"));
                producto.setNombre(resultado.getString("p.nombre"));
                producto.setDescripcion(resultado.getString("p.descripcion"));
                producto.setMarca(resultado.getString("p.marca"));
                producto.setPrecio(resultado.getDouble("p.precio"));
                producto.setImagen(resultado.getString("p.imagen"));
                categoria = new Categoria();
                categoria.setNombre(resultado.getString("c.nombre"));
                producto.setCategoria(categoria);
                productos.add(producto);
            }
        } catch (SQLException e) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
        return productos;
    }

    @Override
    public void closeConnection() {
        ConnectionFactory.closeConexion();
    }
}
