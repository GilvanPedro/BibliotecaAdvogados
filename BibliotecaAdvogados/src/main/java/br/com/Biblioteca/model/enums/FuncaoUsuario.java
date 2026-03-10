package br.com.Biblioteca.model.enums;

public enum FuncaoUsuario {
    ESTAGIARIO(7),
    ADVOGADO(14),
    GERENTE(21),
    SECRETARIO(7),
    SOCIO(14),
    OUTRO(7);

    private final int diasEmprestimo;

    FuncaoUsuario(int diasEmprestimo) {
        this.diasEmprestimo = diasEmprestimo;
    }

    public int getDiasEmprestimo() {
        return diasEmprestimo;
    }
}
