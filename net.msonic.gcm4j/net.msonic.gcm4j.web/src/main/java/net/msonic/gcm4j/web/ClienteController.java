package net.msonic.gcm4j.web;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	
	final static Logger logger = Logger.getLogger(ClienteController.class);
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	
	@RequestMapping(value = "/sendMail/{p1}", method = RequestMethod.GET)
	public String send(@PathVariable String p1){
		
		if(jmsTemplate==null){
			logger.debug("null");
		}else{
			logger.debug("not null");
			
			jmsTemplate.send(new MessageCreator(){

				public Message createMessage(Session session) throws JMSException {
					// TODO Auto-generated method stub
					TextMessage logMessage = session.createTextMessage();
		               logMessage.setText("test de clientes");
		               return logMessage;
				}
				
			});
		}
		
		
		return p1;
	}
}
