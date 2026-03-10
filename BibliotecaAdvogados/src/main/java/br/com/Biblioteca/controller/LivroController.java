package br.com.Biblioteca.controller;

import br.com.Biblioteca.model.entity.HistoricoLeitura;
import br.com.Biblioteca.model.entity.Livro;
import br.com.Biblioteca.model.enums.TipoLeitura;
import br.com.Biblioteca.model.enums.TipoLivro;
import br.com.Biblioteca.repository.HistoricoLeituraRepository;
import br.com.Biblioteca.repository.LivroRepository;
import br.com.Biblioteca.repository.RepositoryManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LivroController {
    private final RepositoryManager repositoryManager;
    private final LivroRepository livroRepository;

    public LivroController() {
        this.repositoryManager = RepositoryManager.getInstance();
        this.livroRepository = repositoryManager.getLivroRepository();
    }

    public long gerarId(){
        List<Livro> listaLivros = livroRepository.getListaLivro();
        long maior = 0;
        for(Livro livro : listaLivros){
            if(livro.getId() > maior){
                maior = livro.getId();
            }
        }
        return maior + 1;
    }

    // ADICIONAR LIVRO
    public String adicionarLivro(String titulo, String autor, String editora, TipoLivro tipoLivro, TipoLeitura tipoLeitura, int quantidade) {
        List<Livro> listaLivros = livroRepository.getListaLivro();

        if(quantidade <= 0){
            return "A quantidade de livros precisa ser maior do que 0";
        }

        long id = gerarId();

        Livro novoLivro = new Livro(
                id,
                titulo,
                autor,
                editora,
                tipoLivro,
                quantidade
        );

        listaLivros.add(novoLivro);
        repositoryManager.salvarTodos();

        return "Livro adicionado com sucesso";
    }

    // DELETA UM LIVRO
    public String deletarLivro(long id){
        List<Livro> listaLivros = livroRepository.getListaLivro();

        Livro l = buscarLivro(id);

        if(l != null){
            listaLivros.remove(l);
            repositoryManager.salvarTodos();
            return "Livro removido com sucesso";
        }else{
            return "Livro não encontrado";
        }
    }

    // MOSTRAR LIVROS CADASTRADOS
    public void mostrarLivros(){
        List<Livro> listaLivros = livroRepository.getListaLivro();

        if(listaLivros.isEmpty()){
            System.out.println("Nenhum livro cadastrado");
            return;
        }

        for(Livro livro : listaLivros){
            System.out.println(livro);
        }
    }

    // EDITAR UM LIVRO JÁ CADASTRADO
    public String editarLivro(long id, String novoTitulo, String novoAutor, String novaEditora, TipoLivro novaCategoria){
        Livro livro = buscarLivro(id);

        if(livro == null){
            return "Livro não encontrado";
        }

        livro.setTitulo(novoTitulo);
        livro.setEditora(novaEditora);
        livro.setAutor(novoAutor);
        livro.setCategoria(novaCategoria);

        repositoryManager.salvarTodos();
        return "Livro editado com sucesso";
    }

    // FAZ A BUSCA POR UM LIVRO DE ACORDO COM O ID
    public Livro buscarLivro(long id){
        List<Livro> listaLivros = livroRepository.getListaLivro();
        for(Livro livro : listaLivros){
            if(livro.getId() == id){
                return livro;
            }
        }
        return null;
    }

    // PROCURA POR UM LIVRO DE ACORDO COM O TÍTULO
    public Livro buscarPorTitulo(String titulo){
        List<Livro> listaLivros = livroRepository.getListaLivro();
        for(Livro livro : listaLivros){
            if(livro.getTitulo().equalsIgnoreCase(titulo)){
                return livro;
            }
        }
        return null;
    }

    // LIVROS MAIS LIDOS
    public String livroMaisLido(){
        HistoricoLeituraRepository historicoRepository = repositoryManager.getHistoricoLeituraRepository();

        Map<String, Integer> contador = new HashMap<>();

        for(HistoricoLeitura h : historicoRepository.getListaHistorico()){
            contador.put(
                    h.getTitulo(),
                    contador.getOrDefault(h.getTitulo(), 0) + 1
            );
        }

        String maisLido = null;
        int maior = 0;

        for(String titulo : contador.keySet()){
            if(contador.get(titulo) > maior){
                maior = contador.get(titulo);
                maisLido = titulo;
            }
        }

        return maisLido;
    }
}
