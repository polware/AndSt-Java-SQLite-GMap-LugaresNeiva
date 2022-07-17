package com.example.lugaresneiva.use_cases;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.lugaresneiva.AboutActivity;
import com.example.lugaresneiva.MapActivity;
import com.example.lugaresneiva.PreferencesActivity;

public class CasosUsoActividades {

    protected Activity actividad;

    public CasosUsoActividades(Activity actividad) {
        this.actividad = actividad;
    }

    public void lanzarAcercaDe() {
        actividad.startActivity(
                new Intent(actividad, AboutActivity.class));
    }

    public void lanzarPreferencias(int codigoSolicitud) {
        actividad.startActivityForResult(
                new Intent(actividad, PreferencesActivity.class), codigoSolicitud);
    }

    public void lanzarMapa() {
        actividad.startActivity(
                new Intent(actividad, MapActivity.class));
    }

}
