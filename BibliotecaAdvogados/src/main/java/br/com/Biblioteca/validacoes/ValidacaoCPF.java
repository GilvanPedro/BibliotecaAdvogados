package br.com.Biblioteca.validacoes;

public class ValidacaoCPF {

    public static boolean validarCPF(String cpf) {

        if (cpf == null) {
            return false;
        }

        // remove pontos e traços
        cpf = cpf.replaceAll("[^0-9]", "");

        // CPF precisa ter 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // evita CPFs tipo 11111111111
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int soma = 0;
            int peso = 10;

            for (int i = 0; i < 9; i++) {
                int num = cpf.charAt(i) - 48;
                soma += num * peso;
                peso--;
            }

            int resto = 11 - (soma % 11);
            int digito1 = (resto == 10 || resto == 11) ? 0 : resto;

            soma = 0;
            peso = 11;

            for (int i = 0; i < 10; i++) {
                int num = cpf.charAt(i) - 48;
                soma += num * peso;
                peso--;
            }

            resto = 11 - (soma % 11);
            int digito2 = (resto == 10 || resto == 11) ? 0 : resto;

            return digito1 == (cpf.charAt(9) - 48) &&
                    digito2 == (cpf.charAt(10) - 48);

        } catch (Exception e) {
            return false;
        }
    }
}