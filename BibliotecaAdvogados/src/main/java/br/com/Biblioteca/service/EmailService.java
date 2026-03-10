package br.com.Biblioteca.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {

    private final String emailSistema = "gilvanpedro2006@gmail.com";
    private final String senha = "sua senha de app";

    public void enviarEmail(String destino, String assunto, String mensagemTexto){

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailSistema, senha);
            }
        });

        try {

            Message mensagem = new MimeMessage(session);

            mensagem.setFrom(new InternetAddress(emailSistema));

            mensagem.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(destino)
            );

            mensagem.setSubject(assunto);

            mensagem.setText(mensagemTexto);

            Transport.send(mensagem);

            System.out.println("Email enviado com sucesso!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

