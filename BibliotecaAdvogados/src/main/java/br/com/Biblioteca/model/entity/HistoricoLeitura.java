package br.com.Biblioteca.model.entity;

import br.com.Biblioteca.model.enums.FuncaoUsuario;
import br.com.Biblioteca.model.enums.StatusDevolucao;
import br.com.Biblioteca.model.enums.TipoLeitura;

import java.time.LocalDate;

public class HistoricoLeitura {
    private long id;
    private String nomeUsuario;
    private String titulo;
    private String autor;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private TipoLeitura tipoLeitura;
    private StatusDevolucao statusDevolucao;

    public HistoricoLeitura(long id, String nomeUsuario, String titulo, String autor, TipoLeitura tipoLeitura, StatusDevolucao statusDevolucao, FuncaoUsuario funcao) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.titulo = titulo;
        this.autor = autor;
        // CADASTRO DO DIA DO EMPRESTIMO E DEVOLUÇÃO FEITOS AUTOMÁTICOS
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucao = LocalDate.now().plusDays(funcao.getDiasEmprestimo());
        this.tipoLeitura = tipoLeitura;
        this.statusDevolucao = statusDevolucao;
    }

    public long getId() {
        return id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public TipoLeitura getTipoLeitura() {
        return tipoLeitura;
    }

    public StatusDevolucao getStatusDevolucao() {
        return statusDevolucao;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public void setTipoLeitura(TipoLeitura tipoLeitura) {
        this.tipoLeitura = tipoLeitura;
    }

    public void setStatusDevolucao(StatusDevolucao statusDevolucao) {
        this.statusDevolucao = statusDevolucao;
    }

    @Override
    public String toString() {
        return "HistoricoLeitura{" +
                "id=" + id +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucao=" + dataDevolucao +
                ", tipoLeitura=" + tipoLeitura +
                ", statusDevolucao=" + statusDevolucao +
                '}';
    }
}
