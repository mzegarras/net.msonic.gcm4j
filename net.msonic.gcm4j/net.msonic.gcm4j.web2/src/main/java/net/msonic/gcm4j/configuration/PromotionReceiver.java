package net.msonic.gcm4j.configuration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PromotionReceiver implements MessageListener {

	private static final Logger logger = LogManager.getLogger(PromotionReceiver.class);

	@Override
	public void onMessage(final Message message) {
		// TODO Auto-generated method stub
		
		
		
		try {
            //LOG.info(((TextMessage) message).getText());
            
            final String msg = ((TextMessage) message).getText();
            

            logger.debug("JMSCorrelationID:{} , message {}", message.getJMSMessageID(),msg);
            
            
            
        }
        catch (JMSException ex) {
       	 logger.error("",ex);
        }
	}

}
