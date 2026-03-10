package br.com.Biblioteca.model.entity;

import br.com.Biblioteca.model.enums.TipoLivro;

public class Livro {
    private long id;
    private String titulo;
    private String autor;
    private String editora;
    private TipoLivro categoria;
    private int quantidade;
    private long quantidadeVezesAlugado;

    public Livro(long id, String titulo, String autor, String editora, TipoLivro categoria, int quantidade) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.quantidadeVezesAlugado = 0;
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public TipoLivro getCategoria() {
        return categoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getEditora() {
        return editora;
    }

    public long getQuantidadeVezesAlugado() {
        return quantidadeVezesAlugado;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setQuantidadeVezesAlugado(long quantidadeVezesAlugado) {
        this.quantidadeVezesAlugado = quantidadeVezesAlugado;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public void setCategoria(TipoLivro categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", editora='" + editora + '\'' +
                ", categoria=" + categoria +
                ", quantidade=" + quantidade +
                ", quantidadeVezesAlugado=" + quantidadeVezesAlugado +
                '}';
    }
}
