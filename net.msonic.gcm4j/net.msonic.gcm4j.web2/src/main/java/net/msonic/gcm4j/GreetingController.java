package net.msonic.gcm4j;


import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.msonic.gcm4j.properties.JmsProperties;
import net.msonic.gcm4j.properties.MailSettings;


@RestController
@RequestMapping(value = "/greeting")
public class GreetingController {

	private static final Logger logger = LogManager.getLogger(GreetingController.class);
	
	@Autowired
	JmsProperties jmsSettings;

	@Autowired
	MailSettings mailSettings;
	
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Autowired
	@Qualifier("receiveDestination")
	Destination receiveDestination;

	@RequestMapping(value = "/hello/{name}",method = RequestMethod.GET)
	String hello(final @PathVariable String name) {
		
		
		UUID uuid = UUID.randomUUID();
		final String randomUUIDString = uuid.toString();
		
		
		Message response = jmsTemplate.sendAndReceive(new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				TextMessage logMessage = session.createTextMessage();
				logMessage.setText(name);
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
