package br.com.Biblioteca.repository;

import br.com.Biblioteca.model.entity.HistoricoLeitura;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoricoLeituraRepository {

    private List<HistoricoLeitura> listaHistorico = new ArrayList<>();

    private final String ARQUIVO = "resources/historicoleitura.json";

    private final Gson gson;

    public HistoricoLeituraRepository(Gson gson) {
        this.gson = gson;
    }

    // SALVAR
    public void salvarArquivo(){
        try{
            File file = new File(ARQUIVO);
            file.getParentFile().mkdirs();

            FileWriter escrita = new FileWriter(file);
            gson.toJson(listaHistorico, escrita);
            escrita.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // LER
    public void lerArquivo(){
        try{
            File file = new File(ARQUIVO);

            if(!file.exists()){
                listaHistorico = new ArrayList<>();
                return;
            }

            FileReader leitura = new FileReader(file);

            Type tipoLista = new TypeToken<List<HistoricoLeitura>>(){}.getType();
            List<HistoricoLeitura> listaLida = gson.fromJson(leitura, tipoLista);

            if(listaLida != null){
                listaHistorico = listaLida;
            } else{
                listaHistorico = new ArrayList<>();
            }

            leitura.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<HistoricoLeitura> getListaHistorico() {
        return listaHistorico;
    }

    // PROCURA POR UM USUÁRIO NO HISTÓRICO
    public List<HistoricoLeitura> buscarPorUsuario(String nome){

        List<HistoricoLeitura> resultado = new ArrayList<>();

        for(HistoricoLeitura h : listaHistorico){
            if(h.getNomeUsuario().equalsIgnoreCase(nome)){
                resultado.add(h);
            }
        }

        return resultado;
    }
}
