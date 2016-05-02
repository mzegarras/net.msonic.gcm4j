package net.msonic.gcm4j;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	JmsProperties jmsSettings;

	@Autowired
	MailSettings mailSettings;
	
	
	@Autowired
	JmsTemplate jmsTemplate;

	@RequestMapping(value = "/hello/{name}",method = RequestMethod.GET)
	String hello(final @PathVariable String name) {
		
		
		jmsTemplate.send(new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				TextMessage logMessage = session.createTextMessage();
				logMessage.setText(name);
				//logMessage.setJMSCorrelationID(randomUUIDString);
				//logMessage.setJMSReplyTo(receiveDestination);
				return logMessage;
			}

		});
		
		
		
		return "Hello, " + name + "!" + jmsSettings.getJndi().getQueueRequest() 
							    + "!" + mailSettings.getFrom() 
							    + "!" + mailSettings.getSmtp().isAuth()
							    + "!" + jmsTemplate.getDefaultDestinationName();
	}
}
