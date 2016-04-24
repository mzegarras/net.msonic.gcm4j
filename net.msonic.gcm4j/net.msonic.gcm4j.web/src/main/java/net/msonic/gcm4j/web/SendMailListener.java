package net.msonic.gcm4j.web;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

public class SendMailListener implements MessageListener {
	
	private static final Logger LOG = Logger.getLogger(SendMailListener.class);
	
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		
		 try {
             LOG.info(((TextMessage) message).getText());
         }
         catch (JMSException ex) {
        	 LOG.error("",ex);
             throw new RuntimeException(ex);
         }
		
		
	}

}
