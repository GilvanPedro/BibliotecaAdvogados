package br.com.Biblioteca.repository;

import br.com.Biblioteca.model.entity.Livro;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LivroRepository {

    private List<Livro> listaLivro = new ArrayList<>();

    private final String ARQUIVO = "resources/livros.json";

    private final Gson gson;

    public LivroRepository(Gson gson) {
        this.gson = gson;
    }

    // SALVAR
    public void salvarArquivo(){
        try{
            File file = new File(ARQUIVO);
            file.getParentFile().mkdirs();

            FileWriter escrita = new FileWriter(file);
            gson.toJson(listaLivro, escrita);
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
                listaLivro = new ArrayList<>();
                return;
            }

            FileReader leitura = new FileReader(file);

            Type tipoLista = new TypeToken<List<Livro>>(){}.getType();
            List<Livro> listaLida = gson.fromJson(leitura, tipoLista);

            if(listaLida != null){
                listaLivro = listaLida;
            } else{
                listaLivro = new ArrayList<>();
            }

            leitura.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Livro> getListaLivro(){
        return listaLivro;
    }
}
