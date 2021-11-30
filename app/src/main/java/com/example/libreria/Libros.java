package com.example.libreria;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Libros extends AppCompatActivity {

    private EditText etISBN, etTitulo, etIdAutorL, etPrecio;
    private Button btRegistrarL, btBuscarL, btBuscarTitulo, btBuscarGeneralL, btModificarL, btEliminar;
    LibreriaDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros);

        etISBN = (EditText) findViewById(R.id.etISBN);
        etTitulo = (EditText) findViewById(R.id.etTitulo);
        etIdAutorL = (EditText) findViewById(R.id.etIdAutorL);
        etPrecio = (EditText) findViewById(R.id.etPrecio);

        btRegistrarL = (Button) findViewById(R.id.btRegistrarL);
        btBuscarL = (Button) findViewById(R.id.btBuscarL);
        btBuscarTitulo = (Button) findViewById(R.id.btBuscarTitulo);
        btBuscarGeneralL = (Button) findViewById(R.id.btBuscarGeneralL);
        btModificarL = (Button) findViewById(R.id.btModificarL);
        btEliminar = (Button) findViewById(R.id.btEliminar);

        mDbHelper = new LibreriaDbHelper(this);
    }

    public void camposVacios () {
        etISBN.setText("");
        etTitulo.setText("");
        etIdAutorL.setText("");
        etPrecio.setText("");
        etISBN.requestFocus();
    }

    public void botonesYcajas (boolean a, boolean b, boolean c, boolean d, boolean e, boolean f, boolean g) {
        btRegistrarL.setEnabled(a);
        btBuscarL.setEnabled(b);
        btBuscarTitulo.setEnabled(c);
        btBuscarGeneralL.setEnabled(d);
        btModificarL.setEnabled(e);
        btEliminar.setEnabled(f);
        etISBN.setEnabled(g);
    }

    public boolean buscarAutor () {
        String id = etIdAutorL.getText().toString();
        Cursor cursor = mDbHelper.getAutorById(id);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public void registrar (View view) {
        if (!etISBN.getText().toString().isEmpty() && !etTitulo.getText().toString().isEmpty() && !etIdAutorL.getText().toString().isEmpty() && !etPrecio.getText().toString().isEmpty()){
            if (buscarAutor()) {
                try {
                    int isbn = Integer.parseInt(etISBN.getText().toString());
                    String titulo = etTitulo.getText().toString();
                    int id = Integer.parseInt(etIdAutorL.getText().toString());
                    float precio = Float.parseFloat(etPrecio.getText().toString());
                    Libro libro = new Libro(isbn, titulo, id, precio);
                    if (mDbHelper.saveLibro(libro) != -1) {
                        Toast.makeText(this, "Libro registrado con exito", Toast.LENGTH_SHORT).show();
                        camposVacios();
                    } else {
                        Toast.makeText(this, "ISBN existente", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Ingrese solo enteros en ISBN", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Autor no registrado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    public void buscar (View view) {
        if (!etISBN.getText().toString().isEmpty()) {
            String isbn = etISBN.getText().toString();
            Cursor cursor = mDbHelper.getLibroById(isbn);
            if (cursor.moveToFirst()) {
                etTitulo.setText(cursor.getString(cursor.getColumnIndex(
                        LibreriaContract.LibrosEntry.COLUMNA_NAME_TITULO)));
                etIdAutorL.setText(cursor.getString(cursor.getColumnIndex(
                        LibreriaContract.LibrosEntry.COLUMNA_NAME_IDAUTOR)));
                etPrecio.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex(
                        LibreriaContract.LibrosEntry.COLUMNA_NAME_PRECIO))));
                botonesYcajas(false, false, true, true, true, true, false);
            } else {
                Toast.makeText(this, "Error al buscar el autor", Toast.LENGTH_SHORT).show();
                camposVacios();
            }
        } else {
            Toast.makeText(this, "Ingrese un ISBN", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    public void buscarTitulo (View view) {
        if (!etTitulo.getText().toString().isEmpty()) {
            String titulo = etTitulo.getText().toString();
            Cursor cursor = mDbHelper.getLibroByTitulo(titulo);
            if (cursor.moveToFirst()) {
                etISBN.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(
                        LibreriaContract.LibrosEntry.COLUMNA_NAME_ISBN))));
                etIdAutorL.setText(cursor.getString(cursor.getColumnIndex(
                        LibreriaContract.LibrosEntry.COLUMNA_NAME_IDAUTOR)));
                etPrecio.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex(
                        LibreriaContract.LibrosEntry.COLUMNA_NAME_PRECIO))));
                botonesYcajas(false, false, true, true, true, true, false);
            } else {
                Toast.makeText(this, "Error al buscar el autor", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingrese un titulo", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscar_general (View view) {
        Intent intent = new Intent(this, ConsGenLibros.class);
        startActivity(intent);
    }

    public void modificar (View view) {
        if (!etTitulo.getText().toString().isEmpty() && !etIdAutorL.getText().toString().isEmpty() && !etPrecio.getText().toString().isEmpty()){
            if (buscarAutor()) {
                try {
                    int isbn = Integer.parseInt(etISBN.getText().toString());
                    String titulo = etTitulo.getText().toString();
                    int id = Integer.parseInt(etIdAutorL.getText().toString());
                    float precio = Float.parseFloat(etPrecio.getText().toString());
                    Libro libro = new Libro(isbn, titulo, id, precio);
                    if (mDbHelper.updateLibro(libro, String.valueOf(isbn)) == 1) {
                        Toast.makeText(this, "Libro modificado correctamente", Toast.LENGTH_SHORT).show();
                        camposVacios();
                        botonesYcajas(true, true, true, true, false, false, true);
                    } else {
                        Toast.makeText(this, "Error al modificar el libro", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Ingrese solo enteros en ISBN y en id autor", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Id de autor no registrado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminar (View view) {
        String isbn = etISBN.getText().toString();
        if (mDbHelper.deleteLibro(isbn) == 1) {
            Toast.makeText(this, "Libro eliminado correctamente", Toast.LENGTH_SHORT).show();
            camposVacios();
            botonesYcajas(true, true, true, true, false, false, true);
        } else {
            Toast.makeText(this, "No se logro eliminar el libro", Toast.LENGTH_SHORT).show();
        }
    }
}