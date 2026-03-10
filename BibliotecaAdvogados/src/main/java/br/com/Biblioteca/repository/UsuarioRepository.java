package br.com.Biblioteca.repository;

import br.com.Biblioteca.model.entity.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    private List<Usuario> listaUsuarios = new ArrayList<>();
    private final String ARQUIVO = "resources/usuarios.json";
    private final Gson gson;

    public UsuarioRepository(Gson gson) {
        this.gson = gson;
    }

    // SALVAR
    public void salvarArquivo(){
        try{
            File file = new File(ARQUIVO);
            file.getParentFile().mkdirs();

            FileWriter escrita = new FileWriter(file);
            gson.toJson(listaUsuarios, escrita);
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
                listaUsuarios = new ArrayList<>();
                return;
            }

            FileReader leitura = new FileReader(file);

            Type tipoLista = new TypeToken<List<Usuario>>(){}.getType();
            List<Usuario> listaLida = gson.fromJson(leitura, tipoLista);

            if(listaLida != null){
                listaUsuarios = listaLida;
            } else{
                listaUsuarios = new ArrayList<>();
            }

            leitura.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Usuario> getListaUsuarios(){
        return listaUsuarios;
    }
}
