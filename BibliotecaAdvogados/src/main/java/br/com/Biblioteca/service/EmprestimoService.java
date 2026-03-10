package br.com.Biblioteca.service;

import br.com.Biblioteca.controller.HistoricoLeituraController;
import br.com.Biblioteca.controller.LivroController;
import br.com.Biblioteca.controller.UsuarioController;
import br.com.Biblioteca.model.entity.HistoricoLeitura;
import br.com.Biblioteca.model.entity.Livro;
import br.com.Biblioteca.model.entity.Usuario;
import br.com.Biblioteca.model.enums.StatusDevolucao;
import br.com.Biblioteca.model.enums.TipoLeitura;
import br.com.Biblioteca.repository.RepositoryManager;

import java.time.LocalDate;

public class EmprestimoService {

    private final RepositoryManager repositoryManager;
    private final UsuarioController usuarioController;
    private final LivroController livroController;
    private final HistoricoLeituraController historicoController;

    public EmprestimoService() {
        this.repositoryManager = RepositoryManager.getInstance();
        this.usuarioController = new UsuarioController();
        this.livroController = new LivroController();
        this.historicoController = new HistoricoLeituraController();
    }

    public String pegarLivro(String cpfUsuario, long idLivro){
        Usuario usuario = usuarioController.buscarUsuario(cpfUsuario);
        Livro livro = livroController.buscarLivro(idLivro);

        if(usuario == null){
            return "Usuário não encontrado";
        }

        if(livro == null){
            return "Livro não encontrado";
        }

        // VERIFICA SE O USUÁRIO JA PEGOU UM LIVRO EMPRESTADO
        if(usuario.isPegouLivro()){
            return "Usuário já possui um livro emprestado";
        }

        TipoLeitura tipo;

        // VERIFICAR A QUANTIDADE
        if(livro.getQuantidade() <= 1){
            tipo = TipoLeitura.PRESENCIAL;

            // ADICIONA OS PONTOS POR LER PRESENCIAL
            usuario.setPontos(usuario.getPontos() + 3);
        } else{
            tipo = TipoLeitura.EMPRESTIMO;

            livro.setQuantidade(livro.getQuantidade() - 1);
            livro.setQuantidadeVezesAlugado(livro.getQuantidadeVezesAlugado() + 1);

            usuario.setPegouLivro(true);

            // PONTOS POR LER UM LIVRO EMPRESTADO
            usuario.setPontos(usuario.getPontos() + 1);
            usuario.setQuantidadeLivrosPegos(usuario.getQuantidadeLivrosPegos() + 1);

            if(usuario.getQuantidadeLivrosPegos() % 10 == 0){
                usuario.setPontos(usuario.getPontos() + 4);
            }

            historicoController.adicionarHistorico(
                    historicoController.gerarId(),
                    usuario.getNome(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    tipo,
                    StatusDevolucao.EM_POSSE,
                    usuario.getFuncao()
            );
        }

        repositoryManager.salvarTodos();

        return "Empréstimo salvo com sucesso";
    }

    public String devolverLivro(long idHistorico){
        HistoricoLeitura historicoLeitura = historicoController.buscarHistorico(idHistorico);

        if(historicoLeitura == null){
            return "Histórico não encontrado";
        }

        Usuario usuario = usuarioController.buscarPorNome(historicoLeitura.getNomeUsuario());
        Livro livro = livroController.buscarPorTitulo(historicoLeitura.getTitulo());

        if(usuario == null || livro == null){
            return "Erro ao localizar usuário ou livro";
        }

        LocalDate hoje = LocalDate.now();

        // VERIFICA O ATRASO
        if(hoje.isAfter(historicoLeitura.getDataDevolucao())){
            historicoLeitura.setStatusDevolucao(StatusDevolucao.ATRASADO);
        } else {
            historicoLeitura.setStatusDevolucao(StatusDevolucao.EM_DIA);

            // PONTOS POR DEVOLVER NO PRAZO
            usuario.setPontos(usuario.getPontos() + 2);
        }

        livro.setQuantidade(livro.getQuantidade() + 1);

        // LIBERA O USUÁRIO PARA PEGAR OUTRO LIVRO
        usuario.setPegouLivro(false);

        repositoryManager.salvarTodos();

        return "Livro devolvido com sucesso";
    }
}
