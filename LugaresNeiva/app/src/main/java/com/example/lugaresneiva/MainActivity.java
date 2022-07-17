package com.example.lugaresneiva;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.lugaresneiva.adapter.AdaptadorBD;
import com.example.lugaresneiva.data.LugaresBD;
import com.example.lugaresneiva.use_cases.CasosUsoActividades;
import com.example.lugaresneiva.use_cases.CasosUsoLocalizacion;
import com.example.lugaresneiva.use_cases.CasosUsoLugar;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.lugaresneiva.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    /*
    View Binding, o vinculación de vistas, es una nueva función que permite acceder a las vistas del layout de la actividad
     de una forma más eficiente y segura. Ya no tendrás que usar el tradicional de findViewById() cada vez que quieras usar
      una vista, View Binding lo hará de forma automática por nosotros
     */
    private static final int SOLICITUD_PERMISO_LOCALIZACION = 1;
    static final int RESULTADO_PREFERENCIAS = 0;
    private LugaresBD lugares;
    private AdaptadorBD adaptador;
    private CasosUsoLugar usoLugar;
    private CasosUsoActividades usoActividades;
    private CasosUsoLocalizacion usoLocalizacion;
    private ActivityMainBinding binding;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicio
        lugares = ((MyApplication) getApplication()).lugares;
        adaptador = ((MyApplication) getApplication()).adaptador;
        usoLugar = new CasosUsoLugar(this, null, lugares, adaptador);
        usoActividades = new CasosUsoActividades(this);
        usoLocalizacion = new CasosUsoLocalizacion(this, SOLICITUD_PERMISO_LOCALIZACION);
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
            @Override public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                usoLugar.nuevo();
            }
        });
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
            usoActividades.lanzarPreferencias(RESULTADO_PREFERENCIAS);
            return true;
        }
        if (id == R.id.menu_acercaDe) {
            usoActividades.lanzarAcercaDe();
            return true;
        }
        if (id == R.id.menu_search) {
            lanzarVistaLugar(null);
            return true;
        }
        if (id==R.id.menu_mapa) {
            usoActividades.lanzarMapa();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode == SOLICITUD_PERMISO_LOCALIZACION
                && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            usoLocalizacion.permisoConcedido();
    }

    // Método que ejecuta actividad ViewPlaceActivity pasándole como posición el lugar a visualizar
    public void lanzarVistaLugar(View view){
        final EditText entrada = new EditText(this);
        entrada.setText("0");
        new AlertDialog.Builder(this)
                .setTitle("Selección de lugar")
                .setMessage("Indica el Id:")
                .setView(entrada)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int id = Integer.parseInt(entrada.getText().toString());
                        usoLugar.mostrar(id);

                    }})
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // LOCALIZACION
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULTADO_PREFERENCIAS) {
            adaptador.setCursor(lugares.extraeCursor());
            adaptador.notifyDataSetChanged();
            if (usoLugar.obtenerFragmentVista() != null)
                usoLugar.mostrar(0);
        }
    }

    @Override protected void onResume() {
        super.onResume();
        usoLocalizacion.activar();
    }

    @Override protected void onPause() {
        super.onPause();
        usoLocalizacion.desactivar();
    }

}