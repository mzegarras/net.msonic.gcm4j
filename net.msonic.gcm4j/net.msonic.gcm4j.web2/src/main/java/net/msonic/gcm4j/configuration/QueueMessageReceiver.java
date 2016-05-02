package net.msonic.gcm4j.configuration;

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


public class QueueMessageReceiver implements MessageListener {

	private static final Logger logger = LogManager.getLogger(QueueMessageReceiver.class);
	
	@Autowired
	@Qualifier("jmsTemplate2")
	JmsTemplate jmsTemplate;
	
	
	@Override
	public void onMessage(final Message message) {
		// TODO Auto-generated method stub
		
		
		 try {
             //LOG.info(((TextMessage) message).getText());
             
             final String msg = ((TextMessage) message).getText();
             

             logger.debug("JMSCorrelationID:{} , message {}", message.getJMSMessageID(),msg);
             
             
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
	}

}
