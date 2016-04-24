package net.msonic.gcm4j.web;

import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	@Qualifier("jmsTemplate1")
	JmsTemplate jmsTemplate;
	
	
	@Autowired
	@Qualifier("receiveDestination")
	Destination receiveDestination;
	

	@RequestMapping(value = "/sendMail/{p1}", method = RequestMethod.GET)
	public String send(final @PathVariable String p1) {
		
		UUID uuid = UUID.randomUUID();
		final String randomUUIDString = uuid.toString();
		
		
		Message response = jmsTemplate.sendAndReceive(new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				TextMessage logMessage = session.createTextMessage();
				logMessage.setText(p1);
				logMessage.setJMSCorrelationID(randomUUIDString);
				logMessage.setJMSReplyTo(receiveDestination);
				return logMessage;
			}

		});
		
		String msg = "";
		
		try {
			msg = ((TextMessage) response).getText();
			logger.debug("Response:" + msg);
		} catch (JMSException ex) {
			// TODO Auto-generated catch block
			logger.error("",ex);
		}

		return msg;
	}
}
