package br.com.Biblioteca.validacoes;

import java.util.regex.Pattern;

public class ValidacaoEmail {

    private static final String REGEX_EMAIL =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final Pattern pattern = Pattern.compile(REGEX_EMAIL);

    public static boolean validarEmail(String email) {

        if(email == null || email.isEmpty()){
            return false;
        }

        return pattern.matcher(email).matches();
    }
}