package es.abatech.beans;

import java.io.Serializable;

/**
 * @author Alex
 * **/
public class Categoria implements Serializable {
    private Byte idCategoria;
    private String nombre;
    private String imagen;

    public Byte getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Byte idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
