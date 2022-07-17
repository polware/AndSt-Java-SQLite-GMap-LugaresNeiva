package com.example.lugaresneiva.adapter;

import android.database.Cursor;

import com.example.lugaresneiva.data.LugaresBD;
import com.example.lugaresneiva.data.RepositorioLugares;
import com.example.lugaresneiva.model.Lugar;

public class AdaptadorBD extends AdaptadorLugares {

    protected Cursor cursor;

    public AdaptadorBD(RepositorioLugares lugares, Cursor cursor) {
        super(lugares);
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public Lugar lugarPosicion(int posicion) {
        cursor.moveToPosition(posicion);
        return LugaresBD.extraeLugar(cursor);
    }

    public int idPosicion(int posicion) {
        cursor.moveToPosition(posicion);
        return cursor.getInt(0);
    }

    public int posicionId(int id) {
        int pos = 0;
        //while (pos == getItemCount() && idPosicion(pos) != id) pos++;
        while (pos < getItemCount() && idPosicion(pos)!= id) pos++;

        if (pos >= getItemCount())
            return -1;
        else
            return pos;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Lugar lugar = lugarPosicion(posicion);
        holder.personaliza(lugar);
        holder.itemView.setTag(new Integer(posicion));
    }

    @Override public int getItemCount() {
        return cursor.getCount();
    }
}
