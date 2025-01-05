package com.example.login;

import androidx.annotation.NonNull;

public class Producto {
    int id;
    String nombre;
    int precio;
    int stock;

    public Producto(int id, String nombre, int precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    @NonNull
    @Override
    public String toString() {
        return "id: " + this.id + ", nombre: " + this.nombre + ", precio: " + this.precio + ", stock: " + this.stock;
    }
}