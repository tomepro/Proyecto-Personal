package com.example.login;

import androidx.annotation.NonNull;

public class Usuario {
    String nombre;
    String contrasena;

    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    @NonNull
    @Override
    public String toString() {
        return "nombre: " + this.nombre + ", contrasena: " + this.contrasena;
    }
}