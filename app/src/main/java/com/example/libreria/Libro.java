package com.example.libreria;

import android.content.ContentValues;

public class Libro {
    private int ISBN;
    private String titulo;
    private int id_autor;
    private float precio;

    public Libro() {}

    public Libro(int ISBN, String titulo, int id_autor, float precio) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.id_autor = id_autor;
        this.precio = precio;
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId_autor() {
        return id_autor;
    }

    public void setId_autor(int id_autor) {
        this.id_autor = id_autor;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public ContentValues toContentValues () {
        ContentValues values = new ContentValues();
        values.put(LibreriaContract.LibrosEntry.COLUMNA_NAME_ISBN, ISBN);
        values.put(LibreriaContract.LibrosEntry.COLUMNA_NAME_TITULO, titulo);
        values.put(LibreriaContract.LibrosEntry.COLUMNA_NAME_IDAUTOR, id_autor);
        values.put(LibreriaContract.LibrosEntry.COLUMNA_NAME_PRECIO, precio);
        return values;
    }
}
