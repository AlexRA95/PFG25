package es.abatech.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Filtro implements Serializable {
    private List<String> categorias = new ArrayList<>();
    private List<String> marcas = new ArrayList<>();
    private Double precioMax;

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public List<String> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<String> marcas) {
        this.marcas = marcas;
    }

    public Double getPrecioMax() {
        return precioMax;
    }

    public void setPrecioMax(Double precioMax) {
        this.precioMax = precioMax;
    }
}
