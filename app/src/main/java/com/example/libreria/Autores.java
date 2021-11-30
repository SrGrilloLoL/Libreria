package com.example.libreria;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Autores extends AppCompatActivity {

    private EditText etIdAutor, etNombre, etApellido;
    private Button btRegistrarA, btBuscarA, btBuscarNombre, btBuscarGeneralA, btModificarA;
    LibreriaDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autores);

        etIdAutor = (EditText) findViewById(R.id.etIdAutor);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellido = (EditText) findViewById(R.id.etApellido);

        btRegistrarA = (Button) findViewById(R.id.btRegistrarA);
        btBuscarA = (Button) findViewById(R.id.btBuscarA);
        btBuscarNombre = (Button) findViewById(R.id.btBuscarNombre);
        btBuscarGeneralA = (Button) findViewById(R.id.btBuscarGeneralA);
        btModificarA = (Button) findViewById(R.id.btModificarA); 

        mDbHelper = new LibreriaDbHelper(this);
    }

    public void camposVacios () {
        etIdAutor.setText("");
        etNombre.setText("");
        etApellido.setText("");
        etIdAutor.requestFocus();
    }

    public void botonesYcajas (boolean a, boolean b, boolean e, boolean f) {
        btRegistrarA.setEnabled(a);
        btBuscarA.setEnabled(b);
        btModificarA.setEnabled(e);
        etIdAutor.setEnabled(f);
    }

    public void registrar (View view) {
        if (!etIdAutor.getText().toString().isEmpty() && !etNombre.getText().toString().isEmpty() && !etApellido.getText().toString().isEmpty()) {
            try {
                int id = Integer.parseInt(etIdAutor.getText().toString());
                String nombre = etNombre.getText().toString();
                String apellido = etApellido.getText().toString();
                Autor autor = new Autor(id, nombre, apellido);
                if (mDbHelper.saveAutor(autor) != -1) {
                    Toast.makeText(this, "Autor registrado con exito", Toast.LENGTH_SHORT).show();
                    camposVacios();
                } else {
                    Toast.makeText(this, "Id de autor ya existente", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Ingrese solo enteros en la ID", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    public void buscar (View view) {
        if (!etIdAutor.getText().toString().isEmpty()){
            String id = etIdAutor.getText().toString();
            Cursor cursor = mDbHelper.getAutorById(id);
            if (cursor.moveToFirst()) {
                etNombre.setText(cursor.getString(cursor.getColumnIndex(
                        LibreriaContract.AutorEntry.COLUMNA_NAME_NOMBRE)));
                etApellido.setText(cursor.getString(cursor.getColumnIndex(
                        LibreriaContract.AutorEntry.COLUMNA_NAME_APELLIDO)));
                botonesYcajas(false, false, true, false);
            } else {
                Toast.makeText(this, "Autor no encontrado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingresa un Id", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    public void buscarNombre (View view) {
        if (!etNombre.getText().toString().isEmpty()) {
            String nombre = etNombre.getText().toString();
            Cursor cursor = mDbHelper.getAutorByNombre(nombre);
            if (cursor.moveToFirst()) {
                etIdAutor.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(
                        LibreriaContract.AutorEntry.COLUMNA_NAME_IDAUTOR))));
                etApellido.setText(cursor.getString(cursor.getColumnIndex(
                        LibreriaContract.AutorEntry.COLUMNA_NAME_APELLIDO)));
                botonesYcajas(false, false,  true, false);
            } else {
                Toast.makeText(this, "Autor no encontrado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingresa un nombre", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscar_general (View view) {
        Intent intent = new Intent(this, ConsGenAutores.class);
        startActivity(intent);
    }

    public void modificar (View view) {
        if (!etNombre.getText().toString().isEmpty() && !etApellido.getText().toString().isEmpty()) {
            try {
                int id = Integer.parseInt(etIdAutor.getText().toString());
                String nombre = etNombre.getText().toString();
                String apellido = etApellido.getText().toString();
                Autor autor = new Autor(id, nombre, apellido);
                if (mDbHelper.updateAutor(autor, String.valueOf(id)) == 1) {
                    Toast.makeText(this, "Autor modificado con exito", Toast.LENGTH_SHORT).show();
                    camposVacios();
                    botonesYcajas(true, true, false, true);
                } else {
                    Toast.makeText(this, "Error al modificar el autor", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Ingrese solo numeros enteros en la ID", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}