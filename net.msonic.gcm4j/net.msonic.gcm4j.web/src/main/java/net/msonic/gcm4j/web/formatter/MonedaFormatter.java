package net.msonic.gcm4j.web.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

public class MonedaFormatter implements Formatter<String> {
	
	@Autowired
    private MessageSource messageSource;

	
	@Override
	public String print(String object, Locale locale) {
		// TODO Auto-generated method stub
		
		if(object.compareTo("804")==0){
			return "S/.";
		}else{
			return "US$ ";
		}
	}

	@Override
	public String parse(String text, Locale locale) throws ParseException {
		// TODO Auto-generated method stub
		return "";
	}

}
