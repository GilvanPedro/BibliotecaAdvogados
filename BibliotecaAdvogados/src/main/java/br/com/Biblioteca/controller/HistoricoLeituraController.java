package br.com.Biblioteca.controller;

import br.com.Biblioteca.model.entity.HistoricoLeitura;
import br.com.Biblioteca.model.enums.FuncaoUsuario;
import br.com.Biblioteca.model.enums.StatusDevolucao;
import br.com.Biblioteca.model.enums.TipoLeitura;
import br.com.Biblioteca.repository.HistoricoLeituraRepository;
import br.com.Biblioteca.repository.RepositoryManager;

import java.util.List;

public class HistoricoLeituraController {
    private final RepositoryManager repositoryManager;
    private final HistoricoLeituraRepository historicoRepository;

    public HistoricoLeituraController() {
        this.repositoryManager = RepositoryManager.getInstance();
        this.historicoRepository = repositoryManager.getHistoricoLeituraRepository();
    }

    public long gerarId(){
        List<HistoricoLeitura> listaHistorico = historicoRepository.getListaHistorico();
        long maior = 0;
        for(HistoricoLeitura historico : listaHistorico){
            if(historico.getId() > maior){
                maior = historico.getId();
            }
        }
        return maior + 1;
    }

    // ADICIONAR HISTÓRICO
    public String adicionarHistorico(long id, String nomeUsuario, String tituloLivro, String autorLivro, TipoLeitura tipo, StatusDevolucao status, FuncaoUsuario funcao){
        List<HistoricoLeitura> listaHistorico = historicoRepository.getListaHistorico();

        HistoricoLeitura novoHistoricoLeitura = new HistoricoLeitura(
                id,
                nomeUsuario,
                tituloLivro,
                autorLivro,
                tipo,
                status,
                funcao
        );
        listaHistorico.add(novoHistoricoLeitura);
        repositoryManager.salvarTodos();

        return "Histórico salvo com sucesso";
    }

    // MOSTRAR HISTÓRICO DE LEITURA
    public void mostrarHistorico(){
        List<HistoricoLeitura> listaHistorico = historicoRepository.getListaHistorico();

        if(listaHistorico.isEmpty()){
            System.out.println("Nada registrado no histórico");
            return;
        }

        for(HistoricoLeitura historico : listaHistorico){
            System.out.println(historico);
        }
    }

    // BUSCAR UM HISTÓRICO PELO ID
    public HistoricoLeitura buscarHistorico(long id){
        List<HistoricoLeitura> listaHistorico = historicoRepository.getListaHistorico();
        for(HistoricoLeitura historico : listaHistorico){
            if(historico.getId() == id){
                return historico;
            }
        }
        return null;
    }
}
