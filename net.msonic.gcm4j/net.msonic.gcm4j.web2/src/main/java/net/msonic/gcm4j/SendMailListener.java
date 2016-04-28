package net.msonic.gcm4j;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;


public class SendMailListener implements MessageListener {
	
	
	
	//http://www.stefanalexandru.com/java/spring-4-jms-connection-with-java-config-and-weblogic-as-jms-provider
	
	 @Autowired
	    private JmsTemplate jmsSenderTemplate;

	
	public void onMessage(final Message message) {
		// TODO Auto-generated method stub
		
		
		System.out.println("onMessage Start");
		
		 try {
             //LOG.info(((TextMessage) message).getText());
             
             final String msg = ((TextMessage) message).getText();
             

             System.out.println( message.getJMSMessageID());
             
             
             jmsSenderTemplate.send(message.getJMSReplyTo(),new MessageCreator() {
				
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
        	 System.out.println("error");
         }
		 
		 
		 /*
		  Message response = session.createMessage();
  response.setJMSCorrelationID(request.getJMSCorrelationID())
 
  producer.send(request.getJMSReplyTo(), response)
		  * */
		
		
	}

}
