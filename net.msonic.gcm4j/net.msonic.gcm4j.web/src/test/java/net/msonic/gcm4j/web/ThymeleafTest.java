package net.msonic.gcm4j.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
	private String htmlContent = null;
	
	private Document documentoHTML=null;
	
	@Autowired
	private TemplateEngine templateEngine;

	@Before
	public void setUp() throws Exception {
		LOG.debug("setUp");
		
		
		final Context ctx = new Context(locale_default);
		
		String target = "27-09-1991 20:29:30";
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date fechaTransaccion =  df.parse(target);
		
		
		TransaccionTO transaccionTO = new TransaccionTO();
		transaccionTO.setName("Manuel");
		transaccionTO.setLastName("Zegarra");
		transaccionTO.setFecha(fechaTransaccion);
		transaccionTO.setNumberTx("122");
		transaccionTO.setCuentaCargo("1122333999");
		transaccionTO.setCuentaCargoMoneda("804");
		transaccionTO.setCuentaCargoAlias("Simple");
		transaccionTO.setCuentaDestino("11223344557");
		transaccionTO.setNombreDestino("ThymeLeaf");
		transaccionTO.setMoneda("804");
		transaccionTO.setMonto(1000);
		
		ctx.setVariable("transaccion", transaccionTO);
		htmlContent = this.templateEngine.process("transferencia", ctx);
		LOG.debug(htmlContent);
		
		documentoHTML = Jsoup.parse(htmlContent);
		
	}

	/*@Test
	public void generateHTML() {

		// Prepare the evaluation context
		final Context ctx = new Context(locale_default);
		ctx.setVariable("nombres", "Nicole Alexandra");
		ctx.setVariable("apellidos", "Zegarra Sànche");

		final String htmlContent = this.templateEngine.process("test", ctx);

		assertNotNull(htmlContent);

		LOG.debug(htmlContent);

	}*/

	@Test
	public void verificarNombreCliente() {
		
		Element table = documentoHTML.select("table.invoice").first();
		Element row = table.select("tr").get(0);
		Element col = row.select("td").get(0);
		String label = col.text();
		
		assertEquals("Verificando Nombre Cliente",label,"Manuel");
		
	}
	
	@Test
	public void verificarFechaHora() {
		
		Element table = documentoHTML.select("table.invoice").first();
		Element row = table.select("tr").get(1);
		Element col = row.select("td").get(0);
		String label = col.text();
		
		assertEquals("Verificando Fecha Transaccion",label,"27/09/1991 20:29");
		
	}

}
