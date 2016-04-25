package net.msonic.gcm4j.web;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.hamcrest.CoreMatchers.*;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.web.client.RestTemplate;
import org.junit.runners.Parameterized;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-test.xml" })
public class ClienteControllerTest {

	private static final Logger LOG = LogManager.getLogger(ClienteControllerTest.class);
	
	
	@Value("${restTemplate.url}")
    private String url;
	
	@Autowired
	private RestTemplate restTemplate;

	// Manually config for spring to use Parameterised
	private TestContextManager testContextManager;

	@Before
	public void setUp() throws Exception {
		this.testContextManager = new TestContextManager(getClass());
		this.testContextManager.prepareTestInstance(this);
	}

	@Test
	public void notNullRestTemplate() {
		assertNotNull(restTemplate);
	}

	@Test
	public void testGetMessage() {

		// String result =
		// restTemplate.getForObject("http://192.168.0.14:9080/gcm4jweb/cliente/sendMail/{parametro}",
		// String.class, "a");

		ResponseEntity<String> response = restTemplate.getForEntity(
				url, String.class, parametro);

		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertThat(response.getBody(), equalTo(esperado));

	}

	@Parameter(value = 0)
	public String parametro;

	@Parameter(value = 1)
	public String esperado;

	@Parameters
	public static Collection<Object[]> data() {
		Collection<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[] { "a", "A" });
		params.add(new Object[] { "b", "B" });
		params.add(new Object[] { "c", "C" });
		params.add(new Object[] { "d", "D" });
		params.add(new Object[] { "e", "E" });

		return params;
	}
}
