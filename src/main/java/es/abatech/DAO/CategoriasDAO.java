package es.abatech.DAO;

import es.abatech.beans.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoriasDAO implements ICategoriasDAO{

    @Override
    public List<Categoria> getCategorias() {
        Connection conexion = null;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        String sql = null;
        Categoria categoria = null;
        List<Categoria> categorias = null;

        try {
            conexion = ConnectionFactory.getConnection();
            sql = "SELECT DISTINCT c.* FROM categorias as c\n" +
                    "JOIN abatech.productos p on c.idCategoria = p.idCategoria ORDER BY c.nombre ASC";
            preparada = conexion.prepareStatement(sql);
            resultado = preparada.executeQuery();
            categorias = new ArrayList<>();
            while (resultado.next()) {
                categoria = new Categoria();
                categoria.setIdCategoria(resultado.getByte("idCategoria"));
                categoria.setNombre(resultado.getString("nombre"));
                categoria.setImagen(resultado.getString("imagen"));
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            Logger.getLogger(CategoriasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }

        return categorias;
    }

    @Override
    public void closeConnection() {
        ConnectionFactory.closeConexion();
    }
}
