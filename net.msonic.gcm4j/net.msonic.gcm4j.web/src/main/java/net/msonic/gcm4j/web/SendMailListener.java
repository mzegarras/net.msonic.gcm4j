package net.msonic.gcm4j.web;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class SendMailListener implements MessageListener {
	
	private static final Logger logger = LogManager.getLogger(SendMailListener.class);
	
	
	@Autowired
	@Qualifier("jmsTemplate2")
	JmsTemplate jmsTemplate;
	
	public void onMessage(final Message message) {
		// TODO Auto-generated method stub
		
		
		
		
		
		 try {
             //LOG.info(((TextMessage) message).getText());
             
             final String msg = ((TextMessage) message).getText();
             logger.info(msg);
             
             
             
             logger.debug("JMSCorrelationID:{} with birthday {}", message.getJMSMessageID());
             
             
             jmsTemplate.send(message.getJMSReplyTo(),new MessageCreator() {
				
				public Message createMessage(Session session) throws JMSException {
					// TODO Auto-generated method stub
					
					
					
					
					TextMessage logMessage = session.createTextMessage();
					logMessage.setJMSCorrelationID(message.getJMSCorrelationID());
					logMessage.setText(msg.toUpperCase());
					return logMessage;
				}
			});
         }
         catch (JMSException ex) {
        	 logger.error("",ex);
         }
		 
		 
		 /*
		  Message response = session.createMessage();
  response.setJMSCorrelationID(request.getJMSCorrelationID())
 
  producer.send(request.getJMSReplyTo(), response)
		  * */
		
		
	}

}
