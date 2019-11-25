package br.ufrpe.wanderlustapp.placaVideo.negocio;

import android.content.Context;
import java.util.List;
import br.ufrpe.wanderlustapp.placaVideo.dominio.PlacaVideo;
import br.ufrpe.wanderlustapp.placaVideo.persistencia.PlacaVideoDAO;


public class PlacaVideoServices {
    private PlacaVideoDAO placaVideoDAO;

    public PlacaVideoServices(Context context) { placaVideoDAO = new PlacaVideoDAO(context); }

    public void cadastrar(PlacaVideo placaVideo) throws Exception {
        if (placaVideoDAO.getPlacaVideoByNome(placaVideo.getNome()) != null){
            throw new Exception();
        }
        long idBar = placaVideoDAO.cadastrar(placaVideo);
        placaVideo.setId(idBar);
    }

    public List<PlacaVideo> getLista(){
        return placaVideoDAO.getListPlacaVideo();
    }

}
