package net.msonic.gcm4j.web;

import java.util.Calendar;
import java.util.Locale;

import static org.junit.Assert.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import net.msonic.gcm4j.web.be.TransaccionTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-test.xml" })
public class ThymeleafTest {

	private static final Logger LOG = LogManager.getLogger(ThymeleafTest.class);
	private static final Locale locale_default = Locale.ENGLISH;

	@Autowired
	private TemplateEngine templateEngine;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void generateHTML() {

		// Prepare the evaluation context
		final Context ctx = new Context(locale_default);
		ctx.setVariable("nombres", "Nicole Alexandra");
		ctx.setVariable("apellidos", "Zegarra Sànche");

		final String htmlContent = this.templateEngine.process("test", ctx);

		assertNotNull(htmlContent);

		LOG.debug(htmlContent);

	}

	@Test
	public void transferTest() {
		final Context ctx = new Context(locale_default);
		
		TransaccionTO transaccionTO = new TransaccionTO();
		transaccionTO.setName("Manuel");
		transaccionTO.setLastName("Zegarra");
		transaccionTO.setFecha(Calendar.getInstance().getTime());
		transaccionTO.setNumberTx("122");
		transaccionTO.setCuentaCargo("1122333999");
		transaccionTO.setCuentaCargoMoneda("804");
		transaccionTO.setCuentaCargoAlias("Simple");
		transaccionTO.setCuentaDestino("11223344557");
		transaccionTO.setNombreDestino("ThymeLeaf");
		transaccionTO.setMoneda("804");
		transaccionTO.setMonto(1000);
		
		
		ctx.setVariable("transaccion", transaccionTO);
		final String htmlContent = this.templateEngine.process("transferencia", ctx);

		assertNotNull(htmlContent);

		LOG.debug(htmlContent);
	}

}
