package com.example.libreria;

import android.provider.BaseColumns;

public class LibreriaContract {

    private LibreriaContract(){}

    public static class AutorEntry implements BaseColumns {
        public static final String TABLE_NAME = "Autores";
        public static final String COLUMNA_NAME_IDAUTOR = "id_autor";
        public static final String COLUMNA_NAME_NOMBRE = "nombre";
        public static final String COLUMNA_NAME_APELLIDO = "apellido";
    }

    public static class LibrosEntry implements BaseColumns {
        public static final String TABLE_NAME = "Libros";
        public static final String COLUMNA_NAME_ISBN = "ISBN";
        public static final String COLUMNA_NAME_TITULO = "titulo";
        public static final String COLUMNA_NAME_IDAUTOR = "id_autor";
        public static final String COLUMNA_NAME_PRECIO = "precio";
    }
}
