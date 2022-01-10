
package com.managetruck.servicios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificacionDeServicio {
    @Autowired(required = true)
    JavaMailSender mailSender;
    
    @Async
    public void enviar(String cuerpo, String titulo, String mail){
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(mail);
        mensaje.setFrom("mailsenderprogramacion@gmail.com");
        mensaje.setSubject(titulo);
        mensaje.setText(cuerpo);
        
        mailSender.send(mensaje);
    }
    public void contactar(String cuerpo,String mail){
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo("mailsenderprogramacion@gmail.com");
        mensaje.setFrom(mail);
        mensaje.setSubject("CONTACT US");
        mensaje.setText("Destinatario: "+mail+"            "+
                cuerpo);
        
        mailSender.send(mensaje);
    }
}