package com.example.lugaresneiva.model;

import com.example.lugaresneiva.R;

public enum TipoLugar {
    OTROS ("Otros", R.drawable.otros),
    RESTAURANTE ("Restaurante", R.drawable.restaurante),
    BAR ("Bar", R.drawable.bar),
    COPAS ("Copas", R.drawable.copas),
    ESPECTACULO ("Espectáculo", R.drawable.espectaculos),
    HOTEL ("Hotel", R.drawable.hotel),
    COMPRAS ("Compras", R.drawable.compras),
    EDUCACION ("Educación", R.drawable.educacion),
    DEPORTE ("Deporte", R.drawable.deporte),
    PARQUE ("Naturaleza", R.drawable.naturaleza),
    GASOLINERA ("Gasolinera", R.drawable.gasolinera),
    HOSPITAL ("Hospital", R.drawable.hospital),
    MUSEO ("Museo", R.drawable.museo);

    private final String texto;
    private final int recurso;

    TipoLugar(String texto, int recurso) {
        this.texto = texto;
        this.recurso = recurso;
    }

    public String getTexto() { return texto; }
    public int getRecurso() { return recurso; }

    public static String[] getNombres() {
        String[] resultado = new String[TipoLugar.values().length];
        for (TipoLugar tipo : TipoLugar.values()) {
            resultado[tipo.ordinal()] = tipo.texto;
        }
        return resultado;
    }

}
