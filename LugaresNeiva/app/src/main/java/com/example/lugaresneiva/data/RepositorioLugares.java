package com.example.lugaresneiva.data;

import com.example.lugaresneiva.model.Lugar;

public interface RepositorioLugares {
    Lugar elemento(int id); //Devuelve el elemento dado su id
    void anade(Lugar lugar); //Añade el elemento indicado
    int nuevo(); //Añade un elemento en blanco y devuelve su id
    void borrar(int id); //Elimina el elemento con el id indicado
    int tamano(); //Devuelve el número de elementos
    void actualiza(int id, Lugar lugar); //Reemplaza un elemento
}
