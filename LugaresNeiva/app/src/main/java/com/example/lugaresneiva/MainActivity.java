package com.example.lugaresneiva;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.lugaresneiva.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    /*
    View Binding, o vinculación de vistas, es una nueva función que permite acceder a las vistas del layout de la actividad
     de una forma más eficiente y segura. Ya no tendrás que usar el tradicional de findViewById() cada vez que quieras usar
      una vista, View Binding lo hará de forma automática por nosotros
     */
    private ActivityMainBinding binding;
    private Button btAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Podemos obtener directamente la vista que contiene el layout entero usando getRoot()
         */
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Barra de acciones
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        //Botón flotante
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Código del botón Acerca De...
        btAbout = findViewById(R.id.button03);
        btAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                aboutApp(null);
            }
        });
    }
    // Método del Acerca De
    public void aboutApp(View view){
        Intent i = new Intent(this, AboutActivity.class);
        startActivity(i);
    }
    // Método de Salir de la app
    public void exitApp(View view){
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}