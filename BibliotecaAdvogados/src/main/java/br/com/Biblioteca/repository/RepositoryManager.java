package br.com.Biblioteca.repository;

import br.com.Biblioteca.util.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;

/**
 * Classe Singleton para gerenciar as instâncias dos repositórios.
 * Garante que apenas uma instância de cada repositório exista na aplicação.
 */
public class RepositoryManager {
    private static RepositoryManager instancia;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;
    private final HistoricoLeituraRepository historicoLeituraRepository;
    private final Gson gson;

    private RepositoryManager() {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        this.gson = gsonBuilder.create();

        this.usuarioRepository = new UsuarioRepository(gson);
        this.livroRepository = new LivroRepository(gson);
        this.historicoLeituraRepository = new HistoricoLeituraRepository(gson);
        
        // Carrega os dados dos arquivos na inicialização
        this.usuarioRepository.lerArquivo();
        this.livroRepository.lerArquivo();
        this.historicoLeituraRepository.lerArquivo();
    }

    public static synchronized RepositoryManager getInstance() {
        if (instancia == null) {
            instancia = new RepositoryManager();
        }
        return instancia;
    }

    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }

    public LivroRepository getLivroRepository() {
        return livroRepository;
    }

    public HistoricoLeituraRepository getHistoricoLeituraRepository() {
        return historicoLeituraRepository;
    }

    /**
     * Salva todos os dados em seus respectivos arquivos.
     * Chamado quando há mudanças que precisam ser persistidas.
     */
    public void salvarTodos() {
        usuarioRepository.salvarArquivo();
        livroRepository.salvarArquivo();
        historicoLeituraRepository.salvarArquivo();
    }
}
