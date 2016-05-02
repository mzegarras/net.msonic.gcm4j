package net.msonic.gcm4j;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

//@SpringBootApplication// same as @Configuration @EnableAutoConfiguration @ComponentScan
@Configuration
@ComponentScan({"net.msonic.gcm4j"})
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

	}

	
	
	
	
	


}