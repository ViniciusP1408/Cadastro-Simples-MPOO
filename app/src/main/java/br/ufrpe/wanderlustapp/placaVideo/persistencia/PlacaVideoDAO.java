package br.ufrpe.wanderlustapp.placaVideo.persistencia;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.placaVideo.dominio.PlacaVideo;
import br.ufrpe.wanderlustapp.cidade.persistencia.CidadeDAO;
import br.ufrpe.wanderlustapp.infra.persistencia.AbstractDAO;
import br.ufrpe.wanderlustapp.infra.persistencia.DBHelper;

public class PlacaVideoDAO extends AbstractDAO{
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public PlacaVideoDAO(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }

    private ContentValues getContentValues(PlacaVideo placaVideo) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_CIDADE_PLACA, placaVideo.getCidade().getId());
        values.put(DBHelper.CAMPO_NOME_PLACA, placaVideo.getNome());
        values.put(DBHelper.CAMPO_DESCRICAO_PLACA, placaVideo.getDescricao());
        return values;
    }

    private Cursor getCursor(List<PlacaVideo> placaVideo, String sql) {
        Cursor cursor = db.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()){
            placaVideo.add(createPlacaVideo(cursor));
        }
        return cursor;
    }
    private void setsBarLocal(Cursor cursor, PlacaVideo placaVideo, CidadeDAO cidadeDAO) {
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_PLACA);
        placaVideo.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NOME_PLACA);
        placaVideo.setNome(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_DESCRICAO_PLACA);
        placaVideo.setDescricao(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_CIDADE_PLACA);
        placaVideo.setCidade(cidadeDAO.getCidade(cursor.getInt(columnIndex)));
    }

    public PlacaVideo getPlacaVideoById(long id) {
        PlacaVideo placaVideo = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PLACA + " WHERE " + DBHelper.CAMPO_ID_PLACA + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        placaVideo = getPlacaVideo(placaVideo, cursor);
        super.close(cursor, db);
        return placaVideo;
    }

    private PlacaVideo getPlacaVideo(PlacaVideo placaVideo, Cursor cursor) {
        if (cursor.moveToFirst()) {
            placaVideo = createPlacaVideo(cursor);
        }
        return placaVideo;
    }

    public PlacaVideo getPlacaVideoByNome(String nome) {
        PlacaVideo placaVideo = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PLACA + " WHERE " + DBHelper.CAMPO_NOME_PLACA + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{nome});
        placaVideo = getPlacaVideo(placaVideo, cursor);
        super.close(cursor, db);
        return placaVideo;
    }

    public List<PlacaVideo> getListPlacaVideo(){
        List<PlacaVideo> bares = new ArrayList<PlacaVideo>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PLACA;
        Cursor cursor = getCursor(bares, sql);
        cursor.close();
        db.close();
        return bares;
    }

    private PlacaVideo createPlacaVideo(Cursor cursor) {
        PlacaVideo placaVideo = new PlacaVideo();
        CidadeDAO cidadeDAO = new CidadeDAO(context);
        setsBarLocal(cursor, placaVideo, cidadeDAO);
        return placaVideo;
    }

    public long cadastrar(PlacaVideo placaVideo){
        db = helper.getWritableDatabase();
        ContentValues values = getContentValues(placaVideo);
        long id = db.insert(DBHelper.TABELA_PLACA,null,values);
        super.close(db);
        return id;
    }
}
