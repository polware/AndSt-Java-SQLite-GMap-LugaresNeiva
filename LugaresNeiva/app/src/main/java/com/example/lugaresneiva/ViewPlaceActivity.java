package com.example.lugaresneiva;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ViewPlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_lugar);
    }
}
    /*
    final static int RESULTADO_EDITAR = 1;
    final static int RESULTADO_GALERIA = 2;
    final static int RESULTADO_FOTO = 3;
    private CacheDBAdapter lugares;
    //private CasosUsoLugar usoLugar;
    private CasosUsoLugarFecha usoLugar;
    private AdaptadorBD adaptador;
    private VistaLugarBinding binding;
    private ImageView foto;
    private Uri uriUltimaFoto;
    private int pos;
    private Lugar lugar;
    private int _id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = VistaLugarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extras = getIntent().getExtras();

        pos = extras.getInt("pos", 0);
        _id = lugares.getAdaptador().idPosicion(pos);
        adaptador = ((MyApplication) getApplication()).adaptador;
        lugares = ((MyApplication) getApplication()).lugares;
        //lugar = lugares.elemento(pos);
        //lugar = lugares.elementoPos(pos);
        lugar = adaptador.lugarPosicion (pos);
        //usoLugar = new CasosUsoLugar(this, lugares, adaptador);
        usoLugar = new CasosUsoLugarFecha(getActivity(), this, lugares, adaptador);
        foto = findViewById(R.id.foto);
        actualizarVistas();

        // Modificacion de la hora
        findViewById(R.id.icono_hora).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        CasosUsoLugarFecha.cambiarHora(pos);
                    }
                });
        findViewById(R.id.hora).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        CasosUsoLugarFecha.cambiarHora(pos);
                    }
                });

        // Modificación de la fecha
        findViewById(R.id.icono_fecha).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        usoLugarFecha.cambiarFecha(pos);
                    }
                });
        findViewById(R.id.fecha).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        usoLugarFecha.cambiarFecha(pos);
                    }
                });
    }

    // Refrescamos con los cambios hechos en EditPlaceActivity
    ActivityResultLauncher edicionLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        actualizarVistas();
                    }
                }
            });

    public void actualizarVistas() {
        //lugar = lugares.elemento(pos);
        lugar = adaptador.lugarPosicion (pos);
        binding.nombre.setText(lugar.getNombre());
        binding.logoTipo.setImageResource(lugar.getTipo().getRecurso());
        binding.tipo.setText(lugar.getTipo().getTexto());
        binding.direccion.setText(lugar.getDireccion());
        binding.telefono.setText(Integer.toString(lugar.getTelefono()));
        binding.url.setText(lugar.getUrl());
        binding.comentario.setText(lugar.getComentario());
        binding.fecha.setText(DateFormat.getDateInstance().format(
                new Date(lugar.getFecha())));
        binding.hora.setText(DateFormat.getTimeInstance().format(
                new Date(lugar.getFecha())));
        binding.valoracion.setOnRatingBarChangeListener(null);
        binding.valoracion.setRating(lugar.getValoracion());
        binding.valoracion.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float valor, boolean fromUser) {
                        //lugar.setValoracion(valor);
                        lugar.setValoracion(valor);
                        usoLugar.actualizaPosLugar(pos, lugar);
                        pos = adaptador.posicionId(_id);
                    }
                });
        usoLugar.visualizarFoto(lugar, foto);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULTADO_EDITAR) {
            lugar = lugares.elemento(_id);
            pos = lugares.getAdaptador().posicionId(_id);
            actualizarVistas();
        } else if (requestCode == RESULTADO_GALERIA) {
            if (resultCode == Activity.RESULT_OK) {
                usoLugar.ponerFoto(pos, data.getDataString(), foto);
            } else {
                Toast.makeText(this, "Foto no cargada", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == RESULTADO_FOTO) {
            if (resultCode == Activity.RESULT_OK && uriUltimaFoto != null) {
                lugar.setFoto(uriUltimaFoto.toString());
                usoLugar.ponerFoto(pos, lugar.getFoto(), foto);
            } else {
                Toast.makeText(this, "Error en captura", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vista_lugar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_compartir:
                usoLugar.compartir(lugar);
                return true;
            case R.id.accion_llegar:
                usoLugar.verMapa(lugar);
                return true;
            case R.id.accion_editar:
                //usoLugar.editar(pos);
                //usoLugar.editar(pos, edicionLauncher);
                usoLugar.editar(pos, RESULTADO_EDITAR);
                return true;
            case R.id.accion_borrar:
                int id = adaptador.idPosicion(pos);
                //usoLugar.borrarPos(pos);
                usoLugar.borrar(id);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void verMapa(View view) {
        usoLugar.verMapa(lugar);
    }

    public void llamarTelefono(View view) {
        usoLugar.llamarTelefono(lugar);
    }

    public void verPgWeb(View view) {
        usoLugar.verPgWeb(lugar);
    }

    public void ponerDeGaleria(View view) {
        usoLugar.ponerDeGaleria(RESULTADO_GALERIA);
    }

    public void tomarFoto(View view) {
        uriUltimaFoto = usoLugar.tomarFoto(RESULTADO_FOTO);
    }

    public void eliminarFoto(View view) {
        usoLugar.ponerFoto(pos, "", foto);
    }
    */

