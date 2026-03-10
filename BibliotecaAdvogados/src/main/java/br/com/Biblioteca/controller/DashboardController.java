package br.com.Biblioteca.controller;

import br.com.Biblioteca.model.entity.Livro;
import br.com.Biblioteca.repository.LivroRepository;
import br.com.Biblioteca.repository.RepositoryManager;
import com.google.gson.Gson;

import java.util.List;

/**
 * Controller responsável por fornecer dados consolidados do dashboard.
 * Utiliza o RepositoryManager para acessar dados já carregados em memória.
 */
public class DashboardController {

    private final RepositoryManager repositoryManager;
    private final LivroController livroController;
    private final UsuarioController usuarioController;
    private LivroRepository livroRepository;

    public DashboardController() {
        this.repositoryManager = RepositoryManager.getInstance();
        this.livroController = new LivroController();
        this.usuarioController = new UsuarioController();
        this.livroRepository = new LivroRepository(new Gson());
    }

    public long totalUsuarios(){
        return repositoryManager
                .getUsuarioRepository()
                .getListaUsuarios()
                .size();
    }

    public long totalLivros(){
        List<Livro> listaLivros = repositoryManager
                .getLivroRepository()
                .getListaLivro();

        long total = 0;

        for(Livro livro : listaLivros){
            total += livro.getQuantidade();
        }
        return total;
    }

    public long totalLivrosUnicos(){
        livroRepository.lerArquivo();
        return livroRepository.getListaLivro().size();
    }

    public long totalEmprestimos(){
        return repositoryManager
                .getHistoricoLeituraRepository()
                .getListaHistorico()
                .size();
    }

    public String livroMaisLido(){
        return livroController.livroMaisLido();
    }

    public String usuarioMaisAtivo(){
        return usuarioController.usuarioMaisAtivo();
    }
}