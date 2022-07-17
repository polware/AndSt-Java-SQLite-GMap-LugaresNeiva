package com.example.lugaresneiva;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lugaresneiva.adapter.AdaptadorBD;
import com.example.lugaresneiva.data.LugaresBD;
import com.example.lugaresneiva.use_cases.CasosUsoLugar;

public class SelectorFragment extends Fragment {
    private LugaresBD lugares;
    private AdaptadorBD adaptador;
    private CasosUsoLugar usoLugar;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor,
                             Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_selector,
                contenedor, false);
        recyclerView = vista.findViewById(R.id.recyclerView);
        return vista;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        lugares = ((MyApplication) getActivity().getApplication()).lugares;
        adaptador = ((MyApplication) getActivity().getApplication()).adaptador;
        usoLugar = new CasosUsoLugar(getActivity(), this, lugares, adaptador);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptador);
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                int pos = (Integer)(v.getTag());
                usoLugar.mostrar(pos);
            }
        });
    }
}
