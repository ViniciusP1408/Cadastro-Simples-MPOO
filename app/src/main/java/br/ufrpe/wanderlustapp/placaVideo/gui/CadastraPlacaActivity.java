package br.ufrpe.wanderlustapp.placaVideo.gui;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.placaVideo.dominio.PlacaVideo;
import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;
import br.ufrpe.wanderlustapp.cidade.negocio.CidadeServices;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pais.dominio.Pais;
import br.ufrpe.wanderlustapp.pais.negocio.PaisServices;

public class CadastraPlacaActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_INSERE = "Cadastrar Placa de Video";
    CidadeServices cidadeServices = new CidadeServices(this);
    PaisServices paisServices = new PaisServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_placas);
        setTitle(TITULO_APPBAR_INSERE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_ponto_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_formulario_ponto_ic_salva){
            PlacaVideo placaVideo = criaBarLocal();
            if(verficaCampos()) {
                Sessao.instance.setPlacaVideo(placaVideo);
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private PlacaVideo criaBarLocal() {
        PlacaVideo placaVideo = new PlacaVideo();
        if (verficaCampos()){
            preencheAtributosBar(placaVideo);
        }
        return placaVideo;
    }

    private boolean verficaCampos(){
        EditText nome = findViewById(R.id.formulario_placa_nome);
        EditText descricao = findViewById(R.id.formulario_placa_descricao);
        return nome.length() > 0 && descricao.length() > 0;
    }

    private void preencheAtributosBar(PlacaVideo placaVideo) {
        EditText nome = findViewById(R.id.formulario_placa_nome);
        EditText descricao = findViewById(R.id.formulario_placa_descricao);
        placaVideo.setNome(nome.getText().toString());
        placaVideo.setDescricao(descricao.getText().toString());
        placaVideo.setCidade(createCidadePadrao());
    }

    private Cidade createCidadePadrao() {
        Pais pais = new Pais();
        pais.setNome("Brasil");
        paisServices.cadastrar(pais);
        Cidade cidade = new Cidade();
        cidade.setNome("Recife");
        cidade.setPais(pais);
        cidadeServices.cadastrar(cidade);
        return cidade;
    }
}
