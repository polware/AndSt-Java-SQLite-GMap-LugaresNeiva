package com.example.lugaresneiva;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lugaresneiva.adapter.AdaptadorBD;
import com.example.lugaresneiva.data.LugaresBD;
import com.example.lugaresneiva.model.GeoPunto;
import com.example.lugaresneiva.model.Lugar;
import com.example.lugaresneiva.model.TipoLugar;
import com.example.lugaresneiva.use_cases.CasosUsoLugar;

public class EditPlaceActivity extends AppCompatActivity {
    private LugaresBD lugares;
    private AdaptadorBD adaptador;
    private CasosUsoLugar usoLugar;
    private Lugar lugar;
    private int pos;
    private int _id;
    private EditText nombre;
    private Spinner tipo;
    private EditText direccion;
    private EditText telefono;
    private EditText url;
    private EditText comentario;
    private EditText longitud, latitud;
    private GeoPunto posicion;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_lugar);
        lugares = ((MyApplication) getApplication()).lugares;
        adaptador = ((MyApplication) getApplication()).adaptador;
        usoLugar = new CasosUsoLugar(this, null, lugares, adaptador);
        Bundle extras = getIntent().getExtras();
        pos = extras.getInt("pos", -1);
        _id = extras.getInt("_id", -1);
        if (_id!=-1) lugar = lugares.elemento(_id);
        else         lugar = adaptador.lugarPosicion (pos);
        actualizarVistas();
    }

    public void actualizarVistas() {
        nombre = findViewById(R.id.nombre);
        nombre.setText(lugar.getNombre());
        direccion = findViewById(R.id.direccion);
        direccion.setText(lugar.getDireccion());
        telefono = findViewById(R.id.telefono);
        telefono.setText(Integer.toString(lugar.getTelefono()));
        posicion = lugar.getPosicion();
        longitud = findViewById(R.id.longitud);
        longitud.setText(Double.toString(posicion.getLongitud()));
        latitud = findViewById(R.id.latitud);
        latitud.setText(Double.toString(posicion.getLatitud()));
        url = findViewById(R.id.url);
        url.setText(lugar.getUrl());
        comentario = findViewById(R.id.comentario);
        comentario.setText(lugar.getComentario());
        tipo = findViewById(R.id.tipo);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TipoLugar.getNombres());
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(adaptador);
        tipo.setSelection(lugar.getTipo().ordinal());
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editplace, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_guardar:
                lugar.setNombre(nombre.getText().toString());
                lugar.setTipo(TipoLugar.values()[tipo.getSelectedItemPosition()]);
                lugar.setDireccion(direccion.getText().toString());
                lugar.setTelefono(Integer.parseInt(telefono.getText().toString()));
                double valorlongitd = Double.parseDouble(longitud.getText().toString());
                double valorlatitud = Double.parseDouble(latitud.getText().toString());
                posicion = new GeoPunto(valorlongitd, valorlatitud);
                lugar.setPosicion(posicion);
                lugar.setUrl(url.getText().toString());
                lugar.setComentario(comentario.getText().toString());
                if (_id==-1) _id = adaptador.idPosicion(pos);
                usoLugar.guardar(_id, lugar);
                finish();
                return true;
            case R.id.accion_cancelar:
                if (_id!=-1) lugares.borrar(_id);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
