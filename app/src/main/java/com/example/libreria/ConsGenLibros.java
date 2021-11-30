package com.example.libreria;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ConsGenLibros extends AppCompatActivity {

    private TextView tvConsultaGeneralL;
    private ListView lvLibros;
    LibreriaDbHelper mDbHelper;

    @Override
    @SuppressLint("Range")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cons_gen_libros);

        mDbHelper = new LibreriaDbHelper(this);

        tvConsultaGeneralL = (TextView) findViewById(R.id.tvConsultaGeneralL);
        lvLibros = (ListView) findViewById(R.id.lvLibros);

        Cursor cursor = mDbHelper.consultaGeneralLibros();
        int cantidad = cursor.getCount();

        int isbn[] = new int[cantidad];
        String titulo[] = new String[cantidad];
        int id[] = new int[cantidad];
        float precio[] = new float[cantidad];

        for (int x = 0; x < cantidad; x++){
            if (cursor.moveToNext()) {
                isbn[x] = cursor.getInt(cursor.getColumnIndex(LibreriaContract.LibrosEntry.COLUMNA_NAME_ISBN));
                titulo[x] = cursor.getString(cursor.getColumnIndex(LibreriaContract.LibrosEntry.COLUMNA_NAME_TITULO));
                id[x] = cursor.getInt(cursor.getColumnIndex(LibreriaContract.LibrosEntry.COLUMNA_NAME_IDAUTOR));
                precio[x] = cursor.getFloat(cursor.getColumnIndex(LibreriaContract.LibrosEntry.COLUMNA_NAME_PRECIO));
            } else {
                Toast.makeText(this, "libros no encontrados", Toast.LENGTH_SHORT).show();
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, titulo);
        lvLibros.setAdapter(adapter);

        lvLibros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mensaje = " ISBN del libro: " + String.valueOf(isbn[i] +
                        " \n Titulo del libro: " + titulo[i] +
                        " \n Id del autor: " + id[i] +
                        " \n Precio del libro: " + String.valueOf(precio[i]));
                tvConsultaGeneralL.setText(mensaje);
            }
        });

    }
}