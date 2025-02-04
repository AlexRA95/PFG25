package es.abatech.beans;

import java.io.Serializable;

public class LineaPedido implements Serializable {
    private Short idLinea;
    private Producto producto;
    private Pedido pedido;
    private Byte cantidad;

    public Short getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(Short idLinea) {
        this.idLinea = idLinea;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Byte getCantidad() {
        return cantidad;
    }

    public void setCantidad(Byte cantidad) {
        this.cantidad = cantidad;
    }
}
