package com.example.lugaresneiva;

import android.app.Application;

import com.example.lugaresneiva.adapter.AdaptadorBD;
import com.example.lugaresneiva.data.LugaresBD;
import com.example.lugaresneiva.model.GeoPunto;

/* Esta clase se extiende a Application para sobreescribir algunos datos de inicio de la aplicación,
 es decir, lo que se especifique aqui se ejecuta antes que la clase MainActivity
*/

// Nota: hay que editar el Manifest del proyecto, agregando esta clase en el nombre de la App:
// android:name=".MyApplication"

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
