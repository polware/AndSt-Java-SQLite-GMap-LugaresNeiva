package com.example.lugaresneiva.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import com.example.lugaresneiva.MyApplication;
import com.example.lugaresneiva.model.GeoPunto;
import com.example.lugaresneiva.model.Lugar;
import com.example.lugaresneiva.model.TipoLugar;

public class LugaresBD extends SQLiteOpenHelper implements RepositorioLugares {

    Context contexto;

    public LugaresBD(Context contexto) {
        super(contexto, "lugares", null, 1);
        this.contexto = contexto;
    }

    @Override public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE lugares ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nombre TEXT, " +
                "direccion TEXT, " +
                "longitud REAL, " +
                "latitud REAL, " +
                "tipo INTEGER, " +
                "foto TEXT, " +
                "telefono INTEGER, " +
                "url TEXT, " +
                "comentario TEXT, " +
                "fecha BIGINT, " +
                "valoracion REAL)");
        bd.execSQL("INSERT INTO lugares VALUES (null, "+
                "'Hotel Neiva Plaza', "+
                "'Calle 7 # 4-62 Neiva (Huila)', -75.28876, 2.92598, "+
                TipoLugar.HOTEL.ordinal() + ", '', 88710806, "+
                "'https://www.hotelneivaplaza.com', "+
                "'Excelente hotel para visitantes.', "+
                System.currentTimeMillis() +", 4.5)");
        bd.execSQL("INSERT INTO lugares VALUES (null, "+
                "'Universidad Nacional Abierta y a Distancia', "+
                "'Carrera 15 # 8-06 Neiva (Huila)', -75.28005, 2.93054, "+
                TipoLugar.EDUCACION.ordinal() + ", '', 88716048, "+
                "'https://www.unad.edu.co', "+
                "'La UNAD se proyecta como una organización líder en educación abierta y a distancia y en ambientes virtuales de aprendizaje.', "+
                System.currentTimeMillis() +", 4.7)");
        bd.execSQL("INSERT INTO lugares VALUES (null, "+
                "'Restaurante y Heladería Frupys', "+
                "'Calle 8 # 31A-80 Neiva (Huila)', -75.26573, 2.93047, "+
                TipoLugar.RESTAURANTE.ordinal() + ", '', 88632401, "+
                "'https://www.facebook.com/frupys', "+
                "'Restaurante con más de 20 años en el sector de elaboración de platos especiales, comidas rápidas y helados.', "+
                System.currentTimeMillis() +", 4.4)");
        bd.execSQL("INSERT INTO lugares VALUES (null, "+
                "'Centro Comercial San Pedro Plaza Comercial', "+
                "'Carrera 8A # 38-42 Neiva (Huila)', -75.28847, 2.95094, "+
                TipoLugar.COMPRAS.ordinal() + ", '', 88640909, "+
                "'https://sanpedroplazacomercial.com.co', "+
                "'Centro comercial con tiendas de ropa, artículos electrónicos y para el hogar, sala de cine y restaurantes.', "+
                System.currentTimeMillis() +", 4.6)");
        bd.execSQL("INSERT INTO lugares VALUES (null, "+
                "'Estadio Guillermo Plazas Alcid', "+
                "'Carrera 16 Neiva (Huila)', -75.28027, 2.93631, "+
                TipoLugar.DEPORTE.ordinal() + ", '', 88750423, "+
                "'http://www.clubatleticohuila.com.co', "+
                "'Estadio de fútbol en donde juega el campeonato el equipo Atlético Huila.', "+
                System.currentTimeMillis() +", 2.8)");
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion,
                                    int newVersion) {
    }

    @Override public Lugar elemento(int id) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM lugares WHERE _id = "+id, null);
        try {
            if (cursor.moveToNext())
                return extraeLugar(cursor);
            else
                throw new SQLException("Error al acceder al elemento _id = "+id);
        } catch (Exception e) {
            throw e;
        } finally {
            if (cursor!=null) cursor.close();
        }
    }

    @Override public void anade(Lugar lugar) {
    }

    @Override public int nuevo() {
        int _id = -1;
        Lugar lugar = new Lugar();
        getWritableDatabase().execSQL("INSERT INTO lugares (nombre, " +
                "direccion, longitud, latitud, tipo, foto, telefono, url, " +
                "comentario, fecha, valoracion) VALUES ('', '',  " +
                lugar.getPosicion().getLongitud() + ","+
                lugar.getPosicion().getLatitud() + ", "+ lugar.getTipo().ordinal()+
                ", '', 0, '', '', " + lugar.getFecha() + ", 0)");
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT _id FROM lugares WHERE fecha = " + lugar.getFecha(), null);
        if (c.moveToNext()) _id = c.getInt(0);
        c.close();
        return _id;
    }

    @Override
    public void borrar(int id) {
        getWritableDatabase().execSQL("DELETE FROM lugares WHERE _id = " + id);
    }

    @Override
    public int tamano() {
        return 0;
    }

    @Override public void actualiza(int id, Lugar lugar) {
        getWritableDatabase().execSQL("UPDATE lugares SET" +
                "   nombre = '" + lugar.getNombre() +
                "', direccion = '" + lugar.getDireccion() +
                "', longitud = " + lugar.getPosicion().getLongitud() +
                " , latitud = " + lugar.getPosicion().getLatitud() +
                " , tipo = " + lugar.getTipo().ordinal() +
                " , foto = '" + lugar.getFoto() +
                "', telefono = " + lugar.getTelefono() +
                " , url = '" + lugar.getUrl() +
                "', comentario = '" + lugar.getComentario() +
                "', fecha = " + lugar.getFecha() +
                " , valoracion = " + lugar.getValoracion() +
                " WHERE _id = " + id);
    }

    public static Lugar extraeLugar(Cursor cursor) {
        Lugar lugar = new Lugar();
        lugar.setNombre(cursor.getString(1));
        lugar.setDireccion(cursor.getString(2));
        lugar.setPosicion(new GeoPunto(cursor.getDouble(3),
                cursor.getDouble(4)));
        lugar.setTipo(TipoLugar.values()[cursor.getInt(5)]);
        lugar.setFoto(cursor.getString(6));
        lugar.setTelefono(cursor.getInt(7));
        lugar.setUrl(cursor.getString(8));
        lugar.setComentario(cursor.getString(9));
        lugar.setFecha(cursor.getLong(10));
        lugar.setValoracion(cursor.getFloat(11));
        return lugar;
    }

    public Cursor extraeCursor() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(contexto);
        String consulta;
        switch (pref.getString("orden", "0")) {
            case "0":
                consulta = "SELECT * FROM lugares ";
                break;
            case "1":
                consulta = "SELECT * FROM lugares ORDER BY valoracion DESC";
                break;
            default:
                double lon = ((MyApplication) contexto.getApplicationContext())
                        .posicionActual.getLongitud();
                double lat = ((MyApplication) contexto.getApplicationContext())
                        .posicionActual.getLatitud();
                consulta = "SELECT * FROM lugares ORDER BY " +
                        "(" + lon + "-longitud)*(" + lon + "-longitud) + " +
                        "(" + lat + "-latitud )*(" + lat + "-latitud )";
                break;
        }
        consulta += " LIMIT "+pref.getString("maximo","12");
        SQLiteDatabase bd = getReadableDatabase();
        return bd.rawQuery(consulta, null);
    }

}
