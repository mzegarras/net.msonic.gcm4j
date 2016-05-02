package net.msonic.gcm4j.properties;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
		private String topicName;
		private String queueAccountRequest;
		private String queuePromotionsRequest;
		private String queueAuditRequest;

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

		public String getTopicName() {
			return topicName;
		}

		public void setTopicName(String topicName) {
			this.topicName = topicName;
		}

		public String getQueueAccountRequest() {
			return queueAccountRequest;
		}

		public void setQueueAccountRequest(String queueAccountRequest) {
			this.queueAccountRequest = queueAccountRequest;
		}

		public String getQueuePromotionsRequest() {
			return queuePromotionsRequest;
		}

		public void setQueuePromotionsRequest(String queuePromotionsRequest) {
			this.queuePromotionsRequest = queuePromotionsRequest;
		}

		public String getQueueAuditRequest() {
			return queueAuditRequest;
		}

		public void setQueueAuditRequest(String queueAuditRequest) {
			this.queueAuditRequest = queueAuditRequest;
		}

		
		
	}
}
