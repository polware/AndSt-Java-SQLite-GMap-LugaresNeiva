package com.example.lugaresneiva;

import android.app.Application;

import com.example.lugaresneiva.adapter.AdaptadorBD;
import com.example.lugaresneiva.data.LugaresBD;
import com.example.lugaresneiva.model.GeoPunto;

public class MyApplication extends Application {

    public LugaresBD lugares;
    public AdaptadorBD adaptador;
    public GeoPunto posicionActual = new GeoPunto(0.0, 0.0);

    @Override public void onCreate() {
        super.onCreate();
        lugares = new LugaresBD(this);
        adaptador = new AdaptadorBD(lugares, lugares.extraeCursor());
    }

}
