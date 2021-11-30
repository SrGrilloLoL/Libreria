package com.example.libreria;

import android.content.ContentValues;

public class Autor {
    private int id_autor;
    private String nombre;
    private String apellido;

    public Autor() {}

    public Autor(int id_autor, String nombre, String apellido) {
        this.id_autor = id_autor;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getId_autor() {
        return id_autor;
    }

    public void setId_autor(int id_autor) {
        this.id_autor = id_autor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public ContentValues toContentValues () {
        ContentValues values = new ContentValues();
        values.put(LibreriaContract.AutorEntry.COLUMNA_NAME_IDAUTOR, id_autor);
        values.put(LibreriaContract.AutorEntry.COLUMNA_NAME_NOMBRE, nombre);
        values.put(LibreriaContract.AutorEntry.COLUMNA_NAME_APELLIDO, apellido);
        return values;
    }
}
