package net.msonic.gcm4j.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.context.IContext;
import org.thymeleaf.testing.templateengine.context.IProcessingContextBuilder;
import org.thymeleaf.testing.templateengine.engine.TestExecutor;
import org.thymeleaf.testing.templateengine.testable.ITest;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-test.xml" })
public class ThymeleafTest2 {
	
	
	private static final TestExecutor executor = new TestExecutor();
	
	
	/*@BeforeClass
	public static void configureSequence() {
		executor.setDialects(Arrays.asList(new StandardDialect(),
				new DataAttributeDialect()));
	}*/

	@Before
	public void configureTest() {
		executor.reset();
	}
	
	@Test
	public void testExisting() {
		
		
		executor.execute("classpath:thymeleaf/test/simple.thtest");
		
		Assert.assertTrue(executor.isAllOK());
	}
	

}
