package net.msonic.gcm4j.configuration;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

import net.msonic.gcm4j.properties.JmsProperties;

@Configuration
@EnableConfigurationProperties(JmsProperties.class)
public class JmsConfiguration {
	
	
	@Autowired
	private JmsProperties jmsProperties;
	
	
	
	@Bean
	public JndiTemplate jndiTemplate() {
	    JndiTemplate jndiTemplate = new JndiTemplate();
	    Properties jndiProps = new Properties();

	    jndiProps.setProperty("java.naming.factory.initial", "com.ibm.websphere.naming.WsnInitialContextFactory");
	    jndiProps.setProperty("java.naming.provider.url", jmsProperties.getJndi().getUrl()); // t3://serverAddress:port
	    //jndiProps.setProperty("java.naming.security.principal", DEFAULT_USER); // injected from properties file username
	    //jndiProps.setProperty("java.naming.security.credentials", DEFAULT_PASSWORD); //injected from properties file password

	    jndiTemplate.setEnvironment(jndiProps);
	    return jndiTemplate;
	}
	
	
	@Bean
	public JndiObjectFactoryBean jmsConnectionFactory() {
	    JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();


	    jndiObjectFactoryBean.setJndiTemplate(jndiTemplate());
	    jndiObjectFactoryBean.setJndiName(jmsProperties.getJndi().getConnection()); // connectionFactory name.
	    
        
	    return jndiObjectFactoryBean;
	}
	
	
	@Bean
	@Primary
	public TransactionAwareConnectionFactoryProxy connectionFactoryProxy() {
	    return new TransactionAwareConnectionFactoryProxy((ConnectionFactory) jmsConnectionFactory().getObject());
	}
	
	@Bean
	public JndiObjectFactoryBean jmsQueueName() {
	    JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();


	    jndiObjectFactoryBean.setJndiTemplate(jndiTemplate());
	    jndiObjectFactoryBean.setJndiName(jmsProperties.getJndi().getQueueRequest()); //queue name

	    return jndiObjectFactoryBean;
	}
	
	@Bean
	public DefaultMessageListenerContainer queueMessageListener() {
		
		DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
	    defaultMessageListenerContainer.setConnectionFactory(connectionFactoryProxy());
	    
	    defaultMessageListenerContainer.setDestination((Destination) jmsQueueName().getObject());
	    
	    defaultMessageListenerContainer.setMessageListener(queueMessageReceiver()); // The actual bean which implements the MessageListener interface
	    defaultMessageListenerContainer.setSessionTransacted(true);
	    defaultMessageListenerContainer.setConcurrentConsumers(1); // how many consumers by default
	    
	    defaultMessageListenerContainer.setMaxConcurrentConsumers(4); // how many consumers when large number of messages have to be processed

	    defaultMessageListenerContainer.afterPropertiesSet();
	    defaultMessageListenerContainer.start();
	    
	    return defaultMessageListenerContainer;
	    
	    
	}
	
	@Bean
	public QueueMessageReceiver queueMessageReceiver() {
	    return new QueueMessageReceiver();
	}
	
	
	
	@Bean
	public JmsTemplate jmsSenderTemplate() {
	    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactoryProxy());

	    jmsTemplate.setSessionTransacted(false);
	    jmsTemplate.setReceiveTimeout(5000);
	    jmsTemplate.setDefaultDestination((Destination) jmsQueueName().getObject());

	    return jmsTemplate;
	}
	
	
}
