package br.com.Biblioteca.service;

import br.com.Biblioteca.controller.UsuarioController;
import br.com.Biblioteca.model.entity.HistoricoLeitura;
import br.com.Biblioteca.model.entity.Usuario;
import br.com.Biblioteca.controller.HistoricoLeituraController;
import br.com.Biblioteca.repository.HistoricoLeituraRepository;
import br.com.Biblioteca.repository.UsuarioRepository;
import com.google.gson.Gson;

import java.time.LocalDate;

public class NotificacaoService {
    UsuarioRepository usuarioRepository = new UsuarioRepository(new Gson());
    HistoricoLeituraRepository historicoLeituraRepository = new HistoricoLeituraRepository(new Gson());
    HistoricoLeituraController historicoController = new HistoricoLeituraController();
    UsuarioController usuarioController = new UsuarioController();
    EmailService emailService = new EmailService();

    public void verificarPrazos(){

        for(HistoricoLeitura historico : historicoLeituraRepository.getListaHistorico()){

            LocalDate hoje = LocalDate.now();

            if(historico.getDataDevolucao().minusDays(2).isEqual(hoje)){

                Usuario usuario = usuarioController.buscarPorNome(historico.getNomeUsuario());

                if(usuario != null){

                    String mensagem =
                            "Olá " + usuario.getNome() + ",\n\n" +
                                    "O prazo de devolução do livro \"" +
                                    historico.getTitulo() +
                                    "\" está próximo.\n" +
                                    "Data de devolução: " +
                                    historico.getDataDevolucao();

                    emailService.enviarEmail(
                            usuario.getEmail(),
                            "Prazo de devolução do livro",
                            mensagem
                    );
                }
            }
        }
    }
}