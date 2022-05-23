package com.astralbrands.orders.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.astralbrands.orders.constants.AppConstants;

@Component
public class CsvFileProcessor implements Processor, AppConstants {

	@Override
	public void process(Exchange exchange) throws Exception {

		String csvFileData = exchange.getProperty(CSV_DATA, String.class);
		String brand = exchange.getProperty(SITE_NAME, String.class);
		exchange.getMessage().setBody(csvFileData);
		exchange.getMessage().setHeader(Exchange.FILE_NAME, exchange.getProperty(INPUT_FILE_NAME) + DOT_CSV);
	}

}
