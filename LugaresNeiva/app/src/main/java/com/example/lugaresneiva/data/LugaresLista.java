package com.example.lugaresneiva.data;

import com.example.lugaresneiva.model.Lugar;
import com.example.lugaresneiva.model.TipoLugar;

import java.util.ArrayList;
import java.util.List;

public class LugaresLista implements RepositorioLugares {
    protected List<Lugar> listaLugares;

    public LugaresLista() {
        listaLugares = new ArrayList<Lugar>();
        anadeEjemplos();
    }

    public Lugar elemento(int id) {
        return listaLugares.get(id);
    }

    public void anade(Lugar lugar) {
        listaLugares.add(lugar);
    }

    public int nuevo() {
        Lugar lugar = new Lugar();
        listaLugares.add(lugar);
        return listaLugares.size()-1;
    }

    public void borrar(int id) {
        listaLugares.remove(id);
    }

    public int tamano() {
        return listaLugares.size();
    }

    public void actualiza(int id, Lugar lugar) {
        listaLugares.set(id, lugar);
    }

    public void anadeEjemplos() {
        anade(new Lugar("Hotel Neiva Plaza",
                "Calle 7 # 4-62 Neiva (Huila)", -75.28876, 2.92598,
                TipoLugar.HOTEL, 88710806,"https://www.hotelneivaplaza.com",
        "Excelente hotel para visitantes.", 4));
        anade(new Lugar("Universidad Nacional Abierta y a Distancia",
                "Carrera 15 # 8-06 Neiva (Huila)", -75.28005, 2.93054,
                TipoLugar.EDUCACION, 88716048,"https://www.unad.edu.co",
                "La UNAD se proyecta como una organización líder en educación abierta y a distancia y en ambientes virtuales de aprendizaje.", 4));
        anade(new Lugar("Restaurante y Heladería Frupys",
                "Calle 8 # 31A-80 Neiva (Huila)", -75.26573, 2.93047,
                TipoLugar.RESTAURANTE, 88632401,"https://www.facebook.com/frupys",
                "Restaurante con más de 20 años en el sector de elaboración de platos especiales, comidas rápidas y helados.", 4));
        anade(new Lugar("Centro Comercial San Pedro Plaza Comercial",
                "Carrera 8A # 38-42 Neiva (Huila)", -75.28847, 2.95094,
                TipoLugar.COMPRAS, 88640909,"https://sanpedroplazacomercial.com.co",
                "Centro comercial con tiendas de ropa, artículos electrónicos y para el hogar, sala de cine y restaurantes.", 4));
        anade(new Lugar("Estadio Guillermo Plazas Alcid", "Carrera 16 Neiva (Huila)", -75.28027, 2.93631,
                TipoLugar.DEPORTE, 88750423, "http://www.clubatleticohuila.com.co",
                "Estadio de fútbol en donde juega el campeonato el equipo Atlético Huila.", 2));
    }
}
