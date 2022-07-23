package com.example.lugaresneiva.use_cases;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.lugaresneiva.EditPlaceActivity;
import com.example.lugaresneiva.MyApplication;
import com.example.lugaresneiva.NewPlaceActivity;
import com.example.lugaresneiva.R;
import com.example.lugaresneiva.ViewPlaceActivity;
import com.example.lugaresneiva.VistaLugarFragment;
import com.example.lugaresneiva.adapter.AdaptadorBD;
import com.example.lugaresneiva.data.LugaresBD;
import com.example.lugaresneiva.model.GeoPunto;
import com.example.lugaresneiva.model.Lugar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CasosUsoLugar {

    protected FragmentActivity actividad;
    protected Fragment fragment;
    protected LugaresBD lugares;
    protected AdaptadorBD adaptador;

    public CasosUsoLugar(FragmentActivity actividad, Fragment fragment,
                         LugaresBD lugares, AdaptadorBD adaptador) {
        this.actividad = actividad;
        this.fragment = fragment;
        this.lugares = lugares;
        this.adaptador = adaptador;
    }

    // OPERACIONES BÁSICAS
    public void actualizaPosLugar(int pos, Lugar lugar) {
        int id = adaptador.idPosicion(pos);
        guardar(id, lugar);
    }

    public void guardar(int id, Lugar nuevoLugar) {
        lugares.actualiza(id, nuevoLugar);
        adaptador.setCursor(lugares.extraeCursor());
        adaptador.notifyDataSetChanged();
    }

    public void mostrar(int pos) {
        VistaLugarFragment fragmentVista = obtenerFragmentVista();
        if (fragmentVista != null) {
            fragmentVista.pos = pos;
            fragmentVista._id = adaptador.idPosicion(pos);
            fragmentVista.actualizaVistas();
        } else {
            Intent i = new Intent(actividad, ViewPlaceActivity.class);
            i.putExtra("pos", pos);
            actividad.startActivity(i);
        }
    }

    public VistaLugarFragment obtenerFragmentVista() {
        FragmentManager manejador = actividad.getSupportFragmentManager();
        return (VistaLugarFragment)
                manejador.findFragmentById(R.id.vista_lugar_fragment);
    }

    public void editar(int pos, int codigoSolicitud) {
        Intent i = new Intent(actividad, EditPlaceActivity.class);
        i.putExtra("pos", pos);
        if (fragment != null)
            fragment.startActivityForResult(i, codigoSolicitud);
        else actividad.startActivityForResult(i, codigoSolicitud);
    }

    public void borrar(int id) {
        lugares.borrar(id);
        adaptador.setCursor(lugares.extraeCursor());
        adaptador.notifyDataSetChanged();
        FragmentManager manejador = actividad.getSupportFragmentManager();
        if (manejador.findFragmentById(R.id.selector_fragment) == null) {
            actividad.finish();
        } else {
            mostrar(0);
        }
    }

    public void nuevo() {
        int id = lugares.nuevo();
        GeoPunto posicion = ((MyApplication) actividad.getApplication())
                .posicionActual;
        if (!posicion.equals(GeoPunto.SIN_POSICION)) {
            Lugar lugar = lugares.elemento(id);
            lugar.setPosicion(posicion);
            lugares.actualiza(id, lugar);
        }
        Intent i = new Intent(actividad, NewPlaceActivity.class);
        i.putExtra("_id", id);
        actividad.startActivity(i);
    }

    // INTENCIONES
    public void compartir(Lugar lugar) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT,
                lugar.getNombre() + " - " + lugar.getUrl());
        actividad.startActivity(i);
    }

    public void llamarTelefono(Lugar lugar) {
        actividad.startActivity(new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + lugar.getTelefono())));
    }

    public void verPgWeb(Lugar lugar) {
        actividad.startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(lugar.getUrl())));
    }

    public final void verMapa(Lugar lugar) {
        double lat = lugar.getPosicion().getLatitud();
        double lon = lugar.getPosicion().getLongitud();
        Uri uri = lugar.getPosicion() != GeoPunto.SIN_POSICION
                ? Uri.parse("geo:" + lat + ',' + lon)
                : Uri.parse("geo:0,0?q=" + lugar.getDireccion());
        actividad.startActivity(new Intent("android.intent.action.VIEW", uri));
    }

    // FOTOGRAFÍAS
    public void ponerDeGaleria(int codigoSolicitud) {
        String action;
        if (android.os.Build.VERSION.SDK_INT >= 19) { // API 19 - Kitkat
            action = Intent.ACTION_OPEN_DOCUMENT;
        } else {
            action = Intent.ACTION_PICK;
        }
        Intent intent = new Intent(action, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (fragment != null)
            fragment.startActivityForResult(intent, codigoSolicitud);
        else actividad.startActivityForResult(intent, codigoSolicitud);
    }

    public void ponerFoto(int pos, String uri, ImageView imageView) {
        Lugar lugar = adaptador.lugarPosicion(pos);
        lugar.setFoto(uri);
        visualizarFoto(lugar, imageView);
        actualizaPosLugar(pos, lugar);
    }

    public void visualizarFoto(Lugar lugar, ImageView imageView) {
        if (lugar.getFoto() != null && !lugar.getFoto().isEmpty()) {
            imageView.setImageBitmap(reduceBitmap(actividad, lugar.getFoto(), 1024, 1024));
        } else {
            imageView.setImageBitmap(null);
        }
    }

    public Uri tomarFoto(int codigoSolicitud) {
        try {
            Uri uriUltimaFoto;
            File file = File.createTempFile(
                    "img_" + (System.currentTimeMillis()/ 1000), ".jpg" ,
                    actividad.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            if (Build.VERSION.SDK_INT >= 24) {
                uriUltimaFoto = FileProvider.getUriForFile(
                        actividad, "es.upv.jtomas.mislugares.fileProvider2", file);
            } else {
                uriUltimaFoto = Uri.fromFile(file);
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra (MediaStore.EXTRA_OUTPUT, uriUltimaFoto);
            if (fragment != null)
                fragment.startActivityForResult(intent, codigoSolicitud);
            else actividad.startActivityForResult(intent, codigoSolicitud);
            return uriUltimaFoto;
        } catch (IOException ex) {
            Toast.makeText(actividad, "Error al crear imagen",
                    Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private Bitmap reduceBitmap(Context contexto, String uri, int maxAncho, int maxAlto) {
        try {
            InputStream input = null;
            Uri u = Uri.parse(uri);
            if (u.getScheme().equals("http") || u.getScheme().equals("https")) {
                input = new URL(uri).openStream();
            } else {
                input = contexto.getContentResolver().openInputStream(u);
            }
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = (int) Math.max(
                    Math.ceil(options.outWidth / maxAncho),
                    Math.ceil(options.outHeight / maxAlto));
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(input, null, options);
        } catch (FileNotFoundException e) {
            Toast.makeText(contexto, "Fichero/recurso de imagen no encontrado",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Toast.makeText(contexto, "Error accediendo a imagen",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
    }

}
