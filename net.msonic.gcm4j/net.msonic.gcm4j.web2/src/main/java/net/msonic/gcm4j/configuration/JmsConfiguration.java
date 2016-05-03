package net.msonic.gcm4j.configuration;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

import net.msonic.gcm4j.properties.JmsProperties;

@Configuration
@EnableConfigurationProperties(JmsProperties.class)
@EnableJms
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
	@Qualifier("receiveDestination")
	public JndiObjectFactoryBean jmsQueueName2() {
	    JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();


	    jndiObjectFactoryBean.setJndiTemplate(jndiTemplate());
	    jndiObjectFactoryBean.setJndiName(jmsProperties.getJndi().getQueueResponse()); //queue name

	    return jndiObjectFactoryBean;
	}
	
	@Bean
	@Qualifier("topic")
	public JndiObjectFactoryBean jmsTopic() {
		JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();


	    jndiObjectFactoryBean.setJndiTemplate(jndiTemplate());
	    jndiObjectFactoryBean.setJndiName(jmsProperties.getJndi().getTopicName()); //queue name
	    
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
	@Primary
	public JmsTemplate jmsSenderTemplate() {
	    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactoryProxy());

	    jmsTemplate.setSessionTransacted(false);
	    jmsTemplate.setReceiveTimeout(5000);
	    jmsTemplate.setDefaultDestination((Destination) jmsQueueName().getObject());
	    jmsTemplate.setPubSubDomain(false);
	    jmsTemplate.setDestinationResolver(new DestinationResolver() {
			
			@Override
			public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain)
					throws JMSException {
				// TODO Auto-generated method stub
				return session.createQueue(destinationName);
			}
		});
	    
	    return jmsTemplate;
	}
	
	@Bean
	@Qualifier("jmsTemplate2")
	public JmsTemplate jmsReceiveTemplate() {
	    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactoryProxy());

	    jmsTemplate.setSessionTransacted(false);
	    jmsTemplate.setReceiveTimeout(5000);
	    jmsTemplate.setPubSubDomain(false);
	    jmsTemplate.setDefaultDestination((Destination) jmsQueueName2().getObject());
	    jmsTemplate.setDestinationResolver(new DestinationResolver() {
			
			@Override
			public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain)
					throws JMSException {
				// TODO Auto-generated method stub
				return session.createQueue(destinationName);
			}
		});
	    return jmsTemplate;
	}
	
	
	@Bean
	@Qualifier("jmsTemplate3")
	public JmsTemplate jmsTopicTemplate() {
	    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactoryProxy());

	    jmsTemplate.setSessionTransacted(false);
	    jmsTemplate.setReceiveTimeout(5000);
	    jmsTemplate.setPubSubDomain(true);
	    jmsTemplate.setDefaultDestination((Destination) jmsTopic().getObject());
jmsTemplate.setDestinationResolver(new DestinationResolver() {
			
			@Override
			public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain)
					throws JMSException {
				// TODO Auto-generated method stub
				return session.createTopic(destinationName);
			}
		});
	    return jmsTemplate;
	}
	
	
	/*
	@Bean
	@Qualifier("queueAccountRequest")
	public JndiObjectFactoryBean jmsQueueAccount() {
	    JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();


	    jndiObjectFactoryBean.setJndiTemplate(jndiTemplate());
	    jndiObjectFactoryBean.setJndiName(jmsProperties.getJndi().getQueueAccountRequest()); //queue name

	    return jndiObjectFactoryBean;
	}
	
	@Bean
	@Qualifier("queuePromotionsRequest")
	public JndiObjectFactoryBean jmsQueuePromotions() {
	    JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();


	    jndiObjectFactoryBean.setJndiTemplate(jndiTemplate());
	    jndiObjectFactoryBean.setJndiName(jmsProperties.getJndi().getQueuePromotionsRequest()); //queue name

	    return jndiObjectFactoryBean;
	}
	
	@Bean
	@Qualifier("queueAuditRequest")
	public JndiObjectFactoryBean jmsQueueAudit() {
	    JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();


	    jndiObjectFactoryBean.setJndiTemplate(jndiTemplate());
	    jndiObjectFactoryBean.setJndiName(jmsProperties.getJndi().getQueueAuditRequest()); //queue name

	    return jndiObjectFactoryBean;
	}*/
	
	

	@Bean
	public AccountReceiver getAccountReceiver() {
	    return new AccountReceiver();
	}
	

	
	@Bean
	public DefaultMessageListenerContainer accountListener() {
		
		
		
		
		DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
		
		
	    defaultMessageListenerContainer.setConnectionFactory(connectionFactoryProxy());
	    
	    //defaultMessageListenerContainer.setConnectionFactory((ConnectionFactory) jmsConnectionFactory2().getObject());
	    
	    defaultMessageListenerContainer.setDestination((Destination) jmsTopic().getObject());
	    
	    defaultMessageListenerContainer.setMessageListener(getAccountReceiver()); // The actual bean which implements the MessageListener interface
	    
	    
	    defaultMessageListenerContainer.setSessionTransacted(false);
	    defaultMessageListenerContainer.setConcurrentConsumers(1); // how many consumers by default
	    
	    defaultMessageListenerContainer.setMaxConcurrentConsumers(4); // how many consumers when large number of messages have to be processed
	    defaultMessageListenerContainer.setPubSubDomain(true);
	    //defaultMessageListenerContainer.setSubscriptionDurable(true);
	    //defaultMessageListenerContainer.setClientId("listener2");
	    //defaultMessageListenerContainer.setDurableSubscriptionName("listener2");
	    defaultMessageListenerContainer.afterPropertiesSet();
	    
	    
	    
	    
	    
	    defaultMessageListenerContainer.start();
	    
	    
	    
	    
	    return defaultMessageListenerContainer;
	    
	    

	}
	
	
	@Bean
	public AuditReceiver getAuditReceiver() {
	    return new AuditReceiver();
	}
	
	
	@Bean
	public DefaultMessageListenerContainer auditListener() {
		
		DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
		defaultMessageListenerContainer.setConnectionFactory(connectionFactoryProxy());
		
	    
	    defaultMessageListenerContainer.setDestination((Destination) jmsTopic().getObject());
	    defaultMessageListenerContainer.setMessageListener(getAuditReceiver()); // The actual bean which implements the MessageListener interface
	    defaultMessageListenerContainer.setSessionTransacted(false);
	    defaultMessageListenerContainer.setConcurrentConsumers(1); // how many consumers by default
	    
	    defaultMessageListenerContainer.setMaxConcurrentConsumers(4); // how many consumers when large number of messages have to be processed
	    defaultMessageListenerContainer.setPubSubDomain(true);
	    
	    //defaultMessageListenerContainer.setSubscriptionDurable(true);
	    //defaultMessageListenerContainer.setClientId("listener1");
	    //defaultMessageListenerContainer.setDurableSubscriptionName("listener1");
	    defaultMessageListenerContainer.afterPropertiesSet();
	    defaultMessageListenerContainer.start();
	    
	    return defaultMessageListenerContainer;
	    
	    
	}
	/*

	@Bean
	public PromotionReceiver getPromotionReceiver() {
	    return new PromotionReceiver();
	}
	
/*
	@Bean
	public DefaultMessageListenerContainer promotionsListener() {
		
		DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
	    defaultMessageListenerContainer.setConnectionFactory(connectionFactoryProxy());
	    
	    defaultMessageListenerContainer.setDestination((Destination) jmsQueuePromotions().getObject());
	    
	    defaultMessageListenerContainer.setMessageListener(getPromotionReceiver()); // The actual bean which implements the MessageListener interface
	    defaultMessageListenerContainer.setSessionTransacted(true);
	    defaultMessageListenerContainer.setConcurrentConsumers(1); // how many consumers by default
	    
	    defaultMessageListenerContainer.setMaxConcurrentConsumers(4); // how many consumers when large number of messages have to be processed

	    defaultMessageListenerContainer.afterPropertiesSet();
	    defaultMessageListenerContainer.start();
	    
	    return defaultMessageListenerContainer;
	    
	    
	}*/
	
	
}
