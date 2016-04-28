package net.msonic.gcm4j;

import java.util.Hashtable;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.WebApplicationInitializer;

import net.msonic.gcm4j.properties.JmsSettings;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

	}

	
	
	@Autowired
	JmsSettings jmsSettings;
	
	@Bean
	public JndiTemplate jndiTemplate() {
	    JndiTemplate jndiTemplate = new JndiTemplate();
	    Properties jndiProps = new Properties();

	    jndiProps.setProperty("java.naming.factory.initial", "com.ibm.websphere.naming.WsnInitialContextFactory");
	    jndiProps.setProperty("java.naming.provider.url", jmsSettings.getJndi().getUrl()); // t3://serverAddress:port
	    //jndiProps.setProperty("java.naming.security.principal", DEFAULT_USER); // injected from properties file username
	    //jndiProps.setProperty("java.naming.security.credentials", DEFAULT_PASSWORD); //injected from properties file password

	    jndiTemplate.setEnvironment(jndiProps);
	    return jndiTemplate;
	}
	
	@Bean
	public JndiObjectFactoryBean jmsConnectionFactory() {
	    JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();


	    jndiObjectFactoryBean.setJndiTemplate(jndiTemplate());
	    jndiObjectFactoryBean.setJndiName(jmsSettings.getJndi().getConnection()); // connectionFactory name.

	    return jndiObjectFactoryBean;
	}
	
	
	@Bean
	public JndiObjectFactoryBean jmsQueueName() {
	    JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();


	    jndiObjectFactoryBean.setJndiTemplate(jndiTemplate());
	    jndiObjectFactoryBean.setJndiName(jmsSettings.getJndi().getQueueRequest()); //queue name

	    return jndiObjectFactoryBean;
	}

	@Bean
	public JndiObjectFactoryBean jmsQueueName2() {
	    JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();


	    jndiObjectFactoryBean.setJndiTemplate(jndiTemplate());
	    jndiObjectFactoryBean.setJndiName(jmsSettings.getJndi().getQueueResponse()); //queue name

	    return jndiObjectFactoryBean;
	}
	
	
	@Bean
	public TransactionAwareConnectionFactoryProxy connectionFactoryProxy() {
	    return new TransactionAwareConnectionFactoryProxy((ConnectionFactory) jmsConnectionFactory().getObject());
	}
	
	
	
	@Bean
	public JmsTemplate jmsSenderTemplate() {
	    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactoryProxy());

	    jmsTemplate.setSessionTransacted(false);
	    jmsTemplate.setReceiveTimeout(5000);
	    jmsTemplate.setDefaultDestination((Destination) jmsQueueName().getObject());

	    return jmsTemplate;
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
	public SendMailListener queueMessageReceiver() {
	    return new SendMailListener();
	}
	
	
	


}