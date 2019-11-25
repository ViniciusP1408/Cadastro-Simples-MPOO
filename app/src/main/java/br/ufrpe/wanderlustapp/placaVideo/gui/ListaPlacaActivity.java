package br.ufrpe.wanderlustapp.placaVideo.gui;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.placaVideo.dominio.PlacaVideo;
import br.ufrpe.wanderlustapp.placaVideo.negocio.PlacaVideoServices;
import br.ufrpe.wanderlustapp.infra.Sessao;


public class ListaPlacaActivity extends AppCompatActivity {

    PlacaVideoServices placaVideoServices = new PlacaVideoServices(this);
    public static final String TITULO_APPBAR_LISTA = "Lista de placas de videos";
    private ListPlacasAdapter adapter;
    private int posicaoEnviada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_placas);
        setTitle(TITULO_APPBAR_LISTA);
        configuraRecyclerview();
        configuraBtnInsereBar();
    }

    private void configuraBtnInsereBar() {
        TextView btnInsereBar = findViewById(R.id.lista_bares_insere_placa);
        btnInsereBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaPlacaActivity.this, CadastraPlacaActivity.class));
            }
        });
    }

    private void insereBar(PlacaVideo placaVideo) {
        try {
            placaVideoServices.cadastrar(placaVideo);
            adapter.adiciona(placaVideo);
            Toast.makeText(getApplicationContext(), "Placa cadastrada", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Placa já cadastrada", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        PlacaVideo placaVideo = Sessao.instance.getPlacaVideo();
        if (placaVideo != null){
            if (placaVideo.getId() == 0){
                insereBar(placaVideo);
                Sessao.instance.resetPlaca();
            }else{
                Sessao.instance.resetPlaca();
            }
        }

        super.onResume();
    }

    private List<PlacaVideo> geraLista(){
        return placaVideoServices.getLista();
    }

    private void setAdapter (RecyclerView recyclerView){
        adapter = new ListPlacasAdapter(this,geraLista());
        recyclerView.setAdapter(adapter);
    }

    private void configuraRecyclerview() {
        RecyclerView listaBares = findViewById(R.id.lista_placas_recyclerview);
        setAdapter(listaBares);
    }

}
