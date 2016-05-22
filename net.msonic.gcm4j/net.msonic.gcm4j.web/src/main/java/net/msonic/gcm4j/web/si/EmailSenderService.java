package net.msonic.gcm4j.web.si;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailSenderService {
	
 private static final Logger logger = LogManager.getLogger(EmailSenderService.class);
 
 
 @Autowired
 private TemplateEngine templateEngine;

	
 
 @Autowired
 JavaMailSender javaMailSender;
 
  @ServiceActivator(inputChannel="busAsync")
  public void send(SendEmailEvent event) {
	  
	  
    logger.info("Received request to send email.");
    //doSendEmailAndStuff(event);
    
    
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom("mzegarrasa@gmail.com");
    mailMessage.setTo(event.getEmailAddress());
    mailMessage.setSubject(event.getSubject());
    mailMessage.setText(event.getContent());
    
    javaMailSender.send(mailMessage);
    
    
  }
}
