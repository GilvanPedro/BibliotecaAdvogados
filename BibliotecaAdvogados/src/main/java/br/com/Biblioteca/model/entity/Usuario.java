package br.com.Biblioteca.model.entity;

import br.com.Biblioteca.model.enums.FuncaoUsuario;

public class Usuario {
    private long id;
    private String nome;
    private String email;
    private String cpf;
    FuncaoUsuario funcao;
    private long quantidadeLivrosPegos;
    private long pontos;
    private boolean pegouLivro;

    public Usuario(long id, String nome, String email, String cpf, FuncaoUsuario funcao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.funcao = funcao;
        this.quantidadeLivrosPegos = 0;
        this.pontos = 0;
        this.pegouLivro = false;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public FuncaoUsuario getFuncao() {
        return funcao;
    }

    public long getQuantidadeLivrosPegos() {
        return quantidadeLivrosPegos;
    }

    public long getPontos() {
        return pontos;
    }

    public boolean isPegouLivro() {
        return pegouLivro;
    }

    public void setQuantidadeLivrosPegos(long quantidadeLivrosPegos) {
        this.quantidadeLivrosPegos = quantidadeLivrosPegos;
    }

    public void setPontos(long pontos) {
        this.pontos = pontos;
    }

    public void setPegouLivro(boolean pegouLivro) {
        this.pegouLivro = pegouLivro;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setFuncao(FuncaoUsuario funcao) {
        this.funcao = funcao;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", funcao=" + funcao +
                ", quantLivrosAlugados=" + quantidadeLivrosPegos +
                ", pontos=" + pontos +
                ", pegouLivro=" + pegouLivro +
                '}';
    }
}
