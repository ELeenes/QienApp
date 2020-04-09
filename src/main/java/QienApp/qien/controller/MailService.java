package QienApp.qien.controller;

import QienApp.qien.domein.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class MailService {

    @Autowired
    GebruikerRepository<Gebruiker> gebruikerRepository;
    @Autowired
    EmailCfg emailCfg;
//    @Autowired
//    EmailBericht emailBericht;

    public void mailVersturen(long contactpersoonId, long urendeclaratieId){
        System.out.println("email versturen");


        Optional<Gebruiker> contactpersoon = gebruikerRepository.findById(contactpersoonId);

        Gebruiker b = contactpersoon.orElse(null);
        System.out.println(b.getEmail());


        // Create a mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailCfg.getHost());
        mailSender.setPort(this.emailCfg.getPort());
        mailSender.setUsername(this.emailCfg.getUsername());
        mailSender.setPassword(this.emailCfg.getPassword());

        // Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("rubenvanrij@gmail.com");
        mailMessage.setTo(b.getEmail());
        mailMessage.setSubject("Goedkeuring vereist voor Qien trainee");
        mailMessage.setText(
        		"Beste " + b.getVoornaam() + " " + b.getAchternaam() + "," + "\n\n" +
                "http://localhost:8082/goedafkeuring.html?uid=" + urendeclaratieId + "\n\n" + "Met vriendelijke groet," + "\n\n" +  "Qien" );

        // Send mail
        mailSender.send(mailMessage);

        System.out.println("Mail sent");
    }
}
