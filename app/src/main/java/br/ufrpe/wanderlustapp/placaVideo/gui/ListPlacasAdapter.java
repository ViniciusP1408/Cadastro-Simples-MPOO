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
import br.ufrpe.wanderlustapp.pontoTuristico.gui.OnItemClickListener;

public class ListPlacasAdapter extends RecyclerView.Adapter<ListPlacasAdapter.PlacaViewHolder>{

private final Context context;
private final List<PlacaVideo> placas;
private OnItemClickListener onItemClickListener;
private ListaPlacaActivity listaBar = new ListaPlacaActivity();

public ListPlacasAdapter(Context context, List<PlacaVideo> placas) {
        this.context = context;
        this.placas = placas;
        }

public List<PlacaVideo> getList(){
        return this.placas;
        }

public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        }

@Override
public PlacaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
        .inflate(R.layout.item_bar, parent, false);
        return new PlacaViewHolder(viewCriada);
        }

@Override
public void onBindViewHolder(@NonNull PlacaViewHolder holder, int position) {
        PlacaVideo bar = placas.get(position);
        holder.vincula(bar);
        }

@Override
public int getItemCount() {
        return placas.size();
        }

public void adiciona(PlacaVideo bar){
        placas.add(bar);
        notifyDataSetChanged();
        }

class PlacaViewHolder extends RecyclerView.ViewHolder {

    private final TextView titulo;
    private final TextView descricao;
    private PlacaVideo placa;

    public PlacaViewHolder(@NonNull View itemView) {
        super(itemView);
        titulo = itemView.findViewById(R.id.item_bar_nome);
        descricao = itemView.findViewById(R.id.item_bar_descricao);
    }

    public void vincula(PlacaVideo bar) {
        this.placa = bar;
        titulo.setText(this.placa.getNome());
        descricao.setText(this.placa.getDescricao());
    }
}
}