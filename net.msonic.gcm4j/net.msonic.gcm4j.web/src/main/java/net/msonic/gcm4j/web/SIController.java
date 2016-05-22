package net.msonic.gcm4j.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.msonic.gcm4j.web.si.BusGateway;
import net.msonic.gcm4j.web.si.EmailSenderService;
import net.msonic.gcm4j.web.si.SendEmailEvent;

@RestController
@RequestMapping("/si")
public class SIController {
	
	@Autowired
	EmailSenderService emailSenderService;
	
	@Autowired
	BusGateway busGateway;
	
	@RequestMapping(value = "/sendSync", method = RequestMethod.GET)
	public String sendSync(final @RequestParam(value = "mail") String p1) {
		
	
		SendEmailEvent sendEmailEvent = new SendEmailEvent();
		sendEmailEvent.setContent("Contenido");
		sendEmailEvent.setSubject("Subject");
		sendEmailEvent.setEmailAddress(p1);
		
		emailSenderService.send(sendEmailEvent); // synchronously send an email
		
		return "Mail Enviado Sync";
		
	}
	
	@RequestMapping(value = "/sendAsync", method = RequestMethod.GET)
	public String sendAync(final @RequestParam(value = "mail") String p1) {
		
		SendEmailEvent sendEmailEvent = new SendEmailEvent();
		sendEmailEvent.setContent("Contenido");
		sendEmailEvent.setSubject("Subject");
		sendEmailEvent.setEmailAddress(p1);
		
		busGateway.onDaBus(sendEmailEvent); // synchronously send an email
		 
		return "Mail Enviado Async";
		
	}
}
