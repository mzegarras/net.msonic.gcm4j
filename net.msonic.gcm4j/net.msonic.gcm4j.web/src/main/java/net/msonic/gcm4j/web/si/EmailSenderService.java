package net.msonic.gcm4j.web.si;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
	
 private static final Logger logger = LogManager.getLogger(EmailSenderService.class);
	
  @ServiceActivator(inputChannel="busAsync")
  public void send(SendEmailEvent event) {
    logger.info("Received request to send email.");
    //doSendEmailAndStuff(event);
    try {
		Thread.sleep(1000 * 5);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}
