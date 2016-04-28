package net.msonic.gcm4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.msonic.gcm4j.properties.JmsSettings;
import net.msonic.gcm4j.properties.MailSettings;


@RestController
@RequestMapping(value = "/greeting")
public class GreetingController {

	@Autowired
	JmsSettings jmsSettings;

	@Autowired
	MailSettings mailSettings;

	@RequestMapping(value = "/hello/{name}",method = RequestMethod.GET)
	String hello(@PathVariable String name) {
		

		
		return "Hello, " + name + "!" + jmsSettings.getJndi().getQueueRequest() 
							    + "!" + mailSettings.getFrom() 
							    + "!" + mailSettings.getSmtp().isAuth();
	}
}
