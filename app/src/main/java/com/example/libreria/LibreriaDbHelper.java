package com.example.libreria;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LibreriaDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "libreria.db";

    public LibreriaDbHelper (Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + LibreriaContract.AutorEntry.TABLE_NAME + " ("
        + LibreriaContract.AutorEntry.COLUMNA_NAME_IDAUTOR + " INTEGER PRIMARY KEY, "
        + LibreriaContract.AutorEntry.COLUMNA_NAME_NOMBRE + " TEXT, "
        + LibreriaContract.AutorEntry.COLUMNA_NAME_APELLIDO + " TEXT)");

        db.execSQL("CREATE TABLE " + LibreriaContract.LibrosEntry.TABLE_NAME + " ("
        + LibreriaContract.LibrosEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + LibreriaContract.LibrosEntry.COLUMNA_NAME_ISBN + " INTEGER, "
        + LibreriaContract.LibrosEntry.COLUMNA_NAME_TITULO + " TEXT, "
        + LibreriaContract.LibrosEntry.COLUMNA_NAME_IDAUTOR + " INTEGER, "
        + LibreriaContract.LibrosEntry.COLUMNA_NAME_PRECIO + " REAL, "
        + " UNIQUE (" + LibreriaContract.LibrosEntry.COLUMNA_NAME_ISBN + "), "
        + " FOREIGN KEY (" + LibreriaContract.LibrosEntry.COLUMNA_NAME_IDAUTOR + ") REFERENCES " + LibreriaContract.AutorEntry.TABLE_NAME + "(" + LibreriaContract.AutorEntry.COLUMNA_NAME_IDAUTOR + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LibreriaContract.AutorEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LibreriaContract.LibrosEntry.TABLE_NAME);
        onCreate(db);
    }

    // Guarda un autor en la Base de datos
    public long saveAutor(Autor autor) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                LibreriaContract.AutorEntry.TABLE_NAME,
                null,
                autor.toContentValues());
    }

    // Guarda un libro en la Base de datos
    public long saveLibro(Libro libro) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                LibreriaContract.LibrosEntry.TABLE_NAME,
                null,
                libro.toContentValues());
    }

    // Consulta general de la tabla autores
    public Cursor consultaGeneralAutores () {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                LibreriaContract.AutorEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

    // Consulta general de la tabla libros
    public Cursor consultaGeneralLibros () {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                LibreriaContract.LibrosEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

    // Busca un autor por ID
    public Cursor getAutorById (String id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                LibreriaContract.AutorEntry.TABLE_NAME,
                null,
                LibreriaContract.AutorEntry.COLUMNA_NAME_IDAUTOR + " LIKE ?",
                new String[]{id},
                null,
                null,
                null);
        return cursor;
    }

    // Busca un libro por ISBN
    public Cursor getLibroById (String id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                LibreriaContract.LibrosEntry.TABLE_NAME,
                null,
                LibreriaContract.LibrosEntry.COLUMNA_NAME_ISBN + " LIKE ?",
                new String[]{id},
                null,
                null,
                null);
        return cursor;
    }

    // Busca un autor por nombre
    public Cursor getAutorByNombre (String nombre) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                LibreriaContract.AutorEntry.TABLE_NAME,
                null,
                LibreriaContract.AutorEntry.COLUMNA_NAME_NOMBRE + " LIKE ?",
                new String[]{nombre},
                null,
                null,
                null);
        return cursor;
    }

    // Busca un libro por titulo
    public Cursor getLibroByTitulo (String titulo) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                LibreriaContract.LibrosEntry.TABLE_NAME,
                null,
                LibreriaContract.LibrosEntry.COLUMNA_NAME_TITULO + " LIKE ?",
                new String[]{titulo},
                null,
                null,
                null);
        return cursor;
    }

    // Eliminar un libro
    public int deleteLibro (String isbn) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(
                LibreriaContract.LibrosEntry.TABLE_NAME,
                LibreriaContract.LibrosEntry.COLUMNA_NAME_ISBN + " LIKE ?",
                new String[]{isbn});
    }

    // Modificar un autor
    public int updateAutor (Autor autor, String id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update(
                LibreriaContract.AutorEntry.TABLE_NAME,
                autor.toContentValues(),
                LibreriaContract.AutorEntry.COLUMNA_NAME_IDAUTOR + " LIKE ?",
                new String[]{id});
    }

    //Modificar un libro
    public int updateLibro (Libro libro, String isbn) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update(
                LibreriaContract.LibrosEntry.TABLE_NAME,
                libro.toContentValues(),
                LibreriaContract.LibrosEntry.COLUMNA_NAME_ISBN + " LIKE ?",
                new String[]{isbn});
    }
}
