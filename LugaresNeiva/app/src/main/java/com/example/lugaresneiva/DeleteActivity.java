package com.example.lugaresneiva;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lugaresneiva.adapter.AdaptadorBD;
import com.example.lugaresneiva.data.LugaresBD;
import com.example.lugaresneiva.model.Lugar;
import com.example.lugaresneiva.use_cases.CasosUsoLugar;

public class DeleteActivity extends AppCompatActivity {
    private AdaptadorBD adaptador;
    private CasosUsoLugar usoLugar;
    private Lugar lugar;
    private LugaresBD lugares;
    private Button btYes;
    private Button btNo;
    public int _id;
    private int pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_id);

        lugares = ((MyApplication) getApplication()).lugares;
        adaptador = ((MyApplication) getApplication()).adaptador;
        usoLugar = new CasosUsoLugar(this, null, lugares, adaptador);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            pos = extras.getInt("pos", 0);
        else pos = 0;
        _id = adaptador.idPosicion(pos);
        lugar = lugares.elemento(_id);
        pos = adaptador.posicionId(_id);

        btYes = findViewById(R.id.bt_si);
        btYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                deleteReg(null);
            }
        });

        btNo = findViewById(R.id.bt_no);
        btNo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cancelDel(null);
            }
        });
    }

    public void deleteReg(View view){
        int id = adaptador.idPosicion(pos);
        usoLugar.borrar(id);
        finish();
    }

    public void cancelDel(View view){
        onBackPressed();
    }

}
