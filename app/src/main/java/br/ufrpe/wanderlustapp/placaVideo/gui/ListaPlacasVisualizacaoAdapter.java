package br.ufrpe.wanderlustapp.placaVideo.gui;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.placaVideo.dominio.PlacaVideo;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pessoa.dominio.Pessoa;

public class ListaPlacasVisualizacaoAdapter extends RecyclerView.Adapter<ListaPlacasVisualizacaoAdapter.PlacaViewHolder> {

    private final Context context;
    private final List<PlacaVideo> placasAvaliacao;

    public ListaPlacasVisualizacaoAdapter(Context context, List<PlacaVideo> bares) {
        this.context = context;
        this.placasAvaliacao = bares;
    }

    @NonNull
    @Override
    public PlacaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_ponto_avaliacao,parent,false);
        return new PlacaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacaViewHolder holder, int position) {
        PlacaVideo placa = placasAvaliacao.get(position);
        holder.vincula(placa);
    }

    @Override
    public int getItemCount() {
        return placasAvaliacao.size();
    }

    class PlacaViewHolder extends RecyclerView.ViewHolder {
        private final TextView titulo;
        private final TextView descricao;
        private PlacaVideo placa;
        private Pessoa pessoa = Sessao.instance.getUsuario().getPessoa();

        public PlacaViewHolder(@NonNull final View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_placa_nome_avaliacao);
            descricao = itemView.findViewById(R.id.item_placa_descricao_avaliacao);

        }

        public void vincula(PlacaVideo placa) {
            this.placa = placa;
            titulo.setText(this.placa.getNome());
            descricao.setText(this.placa.getDescricao());
        }

    }
}

