package net.msonic.gcm4j.configuration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class QueueMessageReceiver implements MessageListener {

	@Override
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub
		
		
		TextMessage msg = null;
		
		
		msg= (TextMessage) arg0;
		
		try {
			System.out.println(msg.getText());
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		}
	}

}
