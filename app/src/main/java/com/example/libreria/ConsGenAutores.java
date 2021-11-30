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

public class ConsGenAutores extends AppCompatActivity {

    private TextView tvConsultaGeneralA;
    private ListView lvAutores;
    LibreriaDbHelper mDbHelper;

    @Override
    @SuppressLint("Range")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cons_gen_autores);

        mDbHelper = new LibreriaDbHelper(this);

        tvConsultaGeneralA = (TextView) findViewById(R.id.tvConsultaGeneralA);
        lvAutores = (ListView) findViewById(R.id.lvAutores);

        Cursor cursor = mDbHelper.consultaGeneralAutores();
        int cantidad = cursor.getCount();

        int id[] = new int[cantidad];
        String nombre[] = new String[cantidad];
        String apellido[] = new String[cantidad];

        for (int x = 0; x < cantidad; x++){
            if (cursor.moveToNext()) {
                id[x] = cursor.getInt(cursor.getColumnIndex(LibreriaContract.AutorEntry.COLUMNA_NAME_IDAUTOR));
                nombre[x] = cursor.getString(cursor.getColumnIndex(LibreriaContract.AutorEntry.COLUMNA_NAME_NOMBRE));
                apellido[x] = cursor.getString(cursor.getColumnIndex(LibreriaContract.AutorEntry.COLUMNA_NAME_APELLIDO));
            } else {
                Toast.makeText(this, "Autores no encontrados", Toast.LENGTH_SHORT).show();
            }
        }

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nombre);
        lvAutores.setAdapter(adapter);

        lvAutores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mensaje = " Id del autor: " + String.valueOf(id[i] +
                        " \n Nombre del autor: " + nombre[i] +
                        " \n Apellido del autor: " + apellido[i]);

                tvConsultaGeneralA.setText(mensaje);
            }
        });
    }
}