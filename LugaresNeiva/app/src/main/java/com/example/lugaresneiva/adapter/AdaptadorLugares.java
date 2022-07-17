package com.example.lugaresneiva.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lugaresneiva.MyApplication;
import com.example.lugaresneiva.R;
import com.example.lugaresneiva.data.RepositorioLugares;
import com.example.lugaresneiva.databinding.ElementoListaBinding;
import com.example.lugaresneiva.model.GeoPunto;
import com.example.lugaresneiva.model.Lugar;

public class AdaptadorLugares extends RecyclerView.Adapter<AdaptadorLugares.ViewHolder> {

    protected RepositorioLugares lugares;         // Lista de lugares a mostrar
    protected View.OnClickListener onClickListener;

    public AdaptadorLugares(RepositorioLugares lugares) {
        this.lugares = lugares;
    }

    //Creamos nuestro ViewHolder con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre, direccion;
        public ImageView foto;
        public RatingBar valoracion;
        public TextView distancia;

        public ViewHolder(ElementoListaBinding itemView) {
            super(itemView.getRoot());
            nombre = itemView.nombre;
            direccion = itemView.direccion;
            foto = itemView.foto;
            valoracion = itemView.valoracion;
            distancia = itemView.distancia;
        }

        // Personalizamos un ViewHolder a partir de un lugar
        public void personaliza(Lugar lugar) {
            nombre.setText(lugar.getNombre());
            direccion.setText(lugar.getDireccion());
            int id = R.drawable.otros;
            switch(lugar.getTipo()) {
                case RESTAURANTE:id = R.drawable.restaurante; break;
                case BAR:    id = R.drawable.bar;     break;
                case COPAS:   id = R.drawable.copas;    break;
                case ESPECTACULO:id = R.drawable.espectaculos; break;
                case HOTEL:   id = R.drawable.hotel;    break;
                case COMPRAS:  id = R.drawable.compras;   break;
                case EDUCACION: id = R.drawable.educacion;  break;
                case DEPORTE:  id = R.drawable.deporte;   break;
                case PARQUE: id = R.drawable.naturaleza; break;
                case GASOLINERA: id = R.drawable.gasolinera; break;
                case HOSPITAL: id = R.drawable.hospital; break;
                case MUSEO: id = R.drawable.museo; break;
            }
            foto.setImageResource(id);
            foto.setScaleType(ImageView.ScaleType.FIT_END);
            valoracion.setRating(lugar.getValoracion());

            GeoPunto pos=((MyApplication) itemView.getContext().getApplicationContext()).posicionActual;
            if (pos.equals(GeoPunto.SIN_POSICION) ||
                    lugar.getPosicion().equals(GeoPunto.SIN_POSICION)) {
                distancia.setText("... Km");
            } else {
                int d=(int) pos.distancia(lugar.getPosicion());
                if (d < 2000) distancia.setText(d + " m");
                else          distancia.setText(d / 1000 + " Km");
            }
        }
    }

    // Creamos el ViewHolder con la vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        ElementoListaBinding v = ElementoListaBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false);
        v.getRoot().setOnClickListener(onClickListener);
        //return new AdaptadorLugares.ViewHolder(v);
        return new ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Lugar lugar = lugares.elemento(posicion);
        holder.personaliza(lugar);
    }

    // Indicamos el número de elementos de la lista
    @Override
    public int getItemCount() {
        return lugares.tamano();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
