package com.example.login;

import androidx.annotation.NonNull;

public class Pedido {
    String producto_id;
    String producto_nombre;
    String talla;

    int cantidad;

    int precio_total;

    public Pedido(String producto_id, String producto_nombre, String talla, int cantidad, int precio_total) {
        this.producto_id = producto_id;
        this.producto_nombre = producto_nombre;
        this.talla = talla;
        this.cantidad = cantidad;
        this.precio_total = precio_total;
    }

    // Getter para precio_total
    public int getPrecioTotal() {
        return precio_total;
    }

    @NonNull
    @Override
    public String toString() {
        return "id: " + this.producto_id +
                ", producto: " + this.producto_nombre +
                ", talla: " + this.talla +
                ", cantidad: " + this.cantidad +
                ", precio: " + this.precio_total;
    }
}
