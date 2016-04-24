package net.msonic.gcm4j.web;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-test.xml"})
public class ClienteControllerTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private RestTemplate restTemplate;
	
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void notNullRestTemplate(){
		assertNotNull(restTemplate);
	}
	
	
	@Test
    public void testGetMessage() {
		 
		 String result = restTemplate.getForObject("http://192.168.0.13:9080/gcm4jweb/cliente/sendMail/{parametro}", String.class, "a");
		 assertThat( result , equalTo("A"));
    }
}
