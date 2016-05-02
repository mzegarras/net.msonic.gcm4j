package net.msonic.gcm4j.properties;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//@ConfigurationProperties(locations = "file:/Proyectos/Apps/config/mail-${spring.profiles.active}.yml", ignoreUnknownFields = false, prefix = "mail")
@ConfigurationProperties(locations = "classpath:jms-${spring.profiles.active}.yml", ignoreUnknownFields = false, prefix = "jms")
public class JmsProperties {

	@NotNull
	private Jndi jndi;

	public Jndi getJndi() {
		return jndi;
	}

	public void setJndi(Jndi jndi) {
		this.jndi = jndi;
	}

	public static class Jndi {

		private String url;
		private String connection;
		private String queueRequest;
		private String queueResponse;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getConnection() {
			return connection;
		}

		public void setConnection(String connection) {
			this.connection = connection;
		}

		public String getQueueRequest() {
			return queueRequest;
		}

		public void setQueueRequest(String queueRequest) {
			this.queueRequest = queueRequest;
		}

		public String getQueueResponse() {
			return queueResponse;
		}

		public void setQueueResponse(String queueResponse) {
			this.queueResponse = queueResponse;
		}
	}
}
